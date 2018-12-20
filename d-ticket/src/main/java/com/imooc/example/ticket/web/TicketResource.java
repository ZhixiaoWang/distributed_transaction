package com.imooc.example.ticket.web;

import com.imooc.example.IOrderService;
import com.imooc.example.dto.OrderDTO;
import com.imooc.example.ticket.dao.TicketRepository;
import com.imooc.example.ticket.domain.Ticket;
import com.imooc.example.ticket.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Shawn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/ticket")
public class TicketResource {
	
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private TicketService ticketService;

    @PostConstruct
    public void init() {
    	if(ticketRepository.count()>0) {
    		return;
    	}
        Ticket ticket = new Ticket();
        ticket.setName("Num.1");
        ticket.setTicketNum(100L);
        ticketRepository.save(ticket);
    }
    
    @PostMapping("/lock")
    public Ticket lock(@RequestBody OrderDTO dto) {
    	return ticketService.ticketLock(dto);
    }
    
    //安全锁票
    @PostMapping("/lock2")
    public int lock2(@RequestBody OrderDTO dto) {
    	return ticketService.ticketLock2(dto);
    }

}
