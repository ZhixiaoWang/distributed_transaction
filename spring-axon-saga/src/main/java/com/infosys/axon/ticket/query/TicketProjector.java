package com.infosys.axon.ticket.query;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.axon.ticket.event.OrderTicketMovedEvent;
import com.infosys.axon.ticket.event.OrderTicketPreservedEvent;
import com.infosys.axon.ticket.event.OrderTicketUnlockedEvent;
import com.infosys.axon.ticket.event.TicketCreatedEvent;

@Component
public class TicketProjector {
	
	@Autowired
	TicketEntityRepository ticketEntityRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(TicketProjector.class);
	
	@EventHandler
	public void on(TicketCreatedEvent event) {
		
		TicketEntity ticket = new TicketEntity();
		ticket.setId(event.getTickeId());
		ticket.setName(event.getName());
		ticket.setLockUser(null);
		ticket.setOwner(null);
		ticketEntityRepository.save(ticket);
		
		LOG.info("Excuted event by TicketProjector: {}", event);
	}
	
	@EventHandler
	public void on(OrderTicketPreservedEvent event) {
		
		TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
		ticket.setLockUser(event.getCustomerId());
		ticketEntityRepository.save(ticket);
		LOG.info("Excuted event by TicketProjector: {}", event);
	}
	
	//交票
	@EventHandler
	public void on(OrderTicketMovedEvent event) {
		
		TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
		ticket.setLockUser(null);
		ticket.setOwner(event.getCustomerId());
		ticketEntityRepository.save(ticket);

		LOG.info("Excuted event by TicketProjector: {}", event);
	}
	
	//交票
	@EventHandler
	public void on(OrderTicketUnlockedEvent event) {
		
		TicketEntity ticket = ticketEntityRepository.findOne(event.getTicketId());
		ticket.setLockUser(null);
		ticketEntityRepository.save(ticket);

		LOG.info("Excuted event by TicketProjector: {}", event);
	}


}
