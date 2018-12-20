package com.imooc.example.ticket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.ticket.dao.TicketRepository;
import com.imooc.example.ticket.domain.Ticket;

@Service
public class TicketService {

	private static final Logger LOG = LoggerFactory.getLogger(TicketService.class);
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = "order:new", containerFactory = "msgFactory")
	public void handleTicketLock(OrderDTO dto) {
		LOG.info("Got new order for ticket lock : {} ", dto);
		int lockCount = ticketRepository.lockTicket(dto.getCustomerId(), dto.getTicketNum());
		
		if(lockCount == 1) {
		//锁票成功
			dto.setStatus("TICKET_LOCKED");
			jmsTemplate.convertAndSend("order:locked", dto);
		} else {
			//错误处理
			dto.setStatus("TICKET_LOCK_FAIL");
			jmsTemplate.convertAndSend("order:fail", dto);
			
		}
	}
	
	@Transactional
	@JmsListener(destination = "order:ticket_move", containerFactory = "msgFactory")
	public void handleTickeMove(OrderDTO dto) {
		LOG.info("Got new order for ticket move : {} ", dto);
//		Ticket ticket = ticketRepository.findOneByTicketNumAndLockUser(dto.getTicketNum(),dto.getCustomerId());
		int count = ticketRepository.moveTicket(dto.getCustomerId(),dto.getTicketNum());
		if(count == 0) {
			LOG.info("Ticket already moved: {}", dto);
		}
		dto.setStatus("TICKET_MOVED");
		jmsTemplate.convertAndSend("order:finish", dto);
	}
	
	@Transactional
	//因为是幂等操作，所以直接放到ticket_error队列中
	@JmsListener(destination = "order:ticket_error", containerFactory = "msgFactory")
	public void handleTickeUnlock(OrderDTO dto) {
		LOG.info("Got new order for ticket unlock : {} ", dto);

		int count = ticketRepository.unLockTicket(dto.getCustomerId(),dto.getTicketNum());
		if(count == 0) {
			LOG.info("Ticket already unlocked: {}", dto);
		}
		
		count = ticketRepository.unMoveTicket(dto.getCustomerId(),dto.getTicketNum());
		
		if(count == 0) {
			LOG.info("Ticket already unmoved or not moved: {}", dto);
		}
		
		jmsTemplate.convertAndSend("order:fail", dto);
	}
	
	
	//问题锁票
	@Transactional
	public Ticket ticketLock(OrderDTO dto) {
		Ticket ticket = ticketRepository.findOneByTicketNum(dto.getTicketNum());
		ticket.setLockUser(dto.getCustomerId());
		ticket = ticketRepository.save(ticket);
		
		//方便终端发起多次请求来进行测试
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		}
		
		return ticket;
	}
	
	//安全锁票
	@Transactional
	public int ticketLock2(OrderDTO dto) {
		
		int lockCount = ticketRepository.lockTicket(dto.getCustomerId(), dto.getTicketNum());
		
		return lockCount;
	}

}
