package com.imooc.example.order.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.order.dao.OrderRepository;
import com.imooc.example.order.domain.Order;

@Service
public class OrderService {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	private Order createOrder(OrderDTO dto) {
		Order order = new Order();
		order.setUuid(dto.getUuid());
		order.setAmount(dto.getAmount());
		order.setCustomerId(dto.getCustomerId());
		order.setTitle(dto.getTitle());
		order.setTicketNum(dto.getTicketNum());
		order.setStatus(dto.getStatus());
		order.setCreatedDate(ZonedDateTime.now());
		
		return order;
	}
	

	@Transactional
	@JmsListener(destination = "order:locked", containerFactory = "msgFactory")
	public void handleOrderNew(OrderDTO dto) {
		LOG.info("Got new order to create : {} ", dto);
		//这里需要根据情况自己生成唯一UUID
		if(orderRepository.findOneByUuid(dto.getUuid()) != null) {
			LOG.info("Msg already processed: {}", dto);
		} else {
			Order order = createOrder(dto);
			orderRepository.save(order);
			dto.setId(order.getId());
		}
		dto.setStatus("NEW");

		jmsTemplate.convertAndSend("order:pay",dto);
	}
	
	
	@Transactional
	@JmsListener(destination = "order:finish", containerFactory = "msgFactory")
	public void handleOrderFinish(OrderDTO order) {
		LOG.info("Got order for order finish : {} ", order);
		Order o  = orderRepository.findOne(order.getId());
		o.setStatus("FINISH");
		orderRepository.save(o);
	}
	
	/**
	 * 错误情况
	 * 1. 一开始锁票失败
	 * 2. 扣费失败后，解锁票，然后触发
	 * 3. 定时任务中 检测到订单超时
	 * @param order
	 */
	@Transactional
	@JmsListener(destination = "order:fail", containerFactory = "msgFactory")
	public void handleOrderFail(OrderDTO dto) {
		LOG.info("Got order for order fail : {} ", dto);
	    Order order ;
		if(dto.getId() == null) {
			order = createOrder(dto);
			order.setStatus("FAIL");
			order.setReason("TICKET_LOCK_FAIL");
		}else {
			order = orderRepository.findOne(dto.getId());
			if(dto.getStatus().equals("NOT_ENOUGH_DEPOSITE")) {
				order.setReason("NOT_ENOUGH_DEPOSITE");
			}
			if(dto.getStatus().equals("TIMEOUT")) {
				order.setReason("TIMEOUT");
			}
		}

		order.setStatus("FAIL");
		orderRepository.save(order);
	}
	
	//定时扫描错误
	@Scheduled(fixedDelay = 1000L)
	public void checkTimeoutOrders(){
		ZonedDateTime checkTime = ZonedDateTime.now().minusMinutes(1L);
		List<Order> orders = orderRepository.findAllByStatusAndCreatedDateBefore("NEW", checkTime);
		orders.forEach(order -> {
			LOG.error("Order timeout {}", order);
			OrderDTO dto = new OrderDTO();
			dto.setId(order.getId());
			dto.setTicketNum(order.getTicketNum());
			dto.setUuid(order.getUuid());
			dto.setAmount(order.getAmount());
			dto.setTitle(order.getTitle());
			dto.setCustomerId(order.getCustomerId());
			dto.setStatus("TIMEOUT");
			jmsTemplate.convertAndSend("order:fail", dto);
		});
	}

}
