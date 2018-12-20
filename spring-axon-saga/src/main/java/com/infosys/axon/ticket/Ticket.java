package com.infosys.axon.ticket;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosys.axon.ticket.command.OrderTicketMoveCommand;
import com.infosys.axon.ticket.command.OrderTicketPreserveCommand;
import com.infosys.axon.ticket.command.OrderTicketUnlockCommand;
import com.infosys.axon.ticket.command.TicketCreateCommand;
import com.infosys.axon.ticket.event.OrderTicketMovedEvent;
import com.infosys.axon.ticket.event.OrderTicketPreserveFailedEvent;
import com.infosys.axon.ticket.event.OrderTicketPreservedEvent;
import com.infosys.axon.ticket.event.OrderTicketUnlockedEvent;
import com.infosys.axon.ticket.event.TicketCreatedEvent;

@Aggregate
public class Ticket {
	
	private static final Logger LOG = LoggerFactory.getLogger(Ticket.class);
	
	@AggregateIdentifier
	private String ticketId;
	
	private String name;
	
	private String 	lockUser;
	
	private String owner;

	public Ticket() {
		
	}
	
	@CommandHandler
	public Ticket(TicketCreateCommand command) {
		apply(new TicketCreatedEvent(command.getTicketId(), command.getName()));
	}
	
	//锁票
	@CommandHandler
	public void handle(OrderTicketPreserveCommand command) {
		//是否已被买
		if(this.owner != null) {
			LOG.error("Ticket is owner");
			apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
		//是否已被锁
		} else if(this.lockUser != null && !this.lockUser.equals(command.getCustomerId())) {
			LOG.info("Duplicated command");
		} else if(this.lockUser == null) {
			apply(new OrderTicketPreservedEvent(command.getTicketId(),command.getOrderId(), command.getCustomerId()));
		} else {
			apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
		}	
	}
	
	//交票
	@CommandHandler
	public void handle(OrderTicketMoveCommand command) {
		if(this.lockUser == null) {
			LOG.error("Invalid command, ticket not locked");
		} else if(!this.lockUser.equals(command.getCustomerId())) {
			LOG.error("Invalid command, ticket not locked by customer.");
		} else {
			apply(new OrderTicketMovedEvent(command.getTicketId(), command.getOrderId(), command.getCustomerId()));
		}
	}
	
	@CommandHandler
	public void handle(OrderTicketUnlockCommand command) {
		if(this.lockUser == null) {
			LOG.error("Invalid command, ticket not locked");
		} else if(!this.lockUser.equals(command.getCustomerId())) {
			LOG.error("Invalid command, ticket not locked by customer.");
		} else {
			apply(new OrderTicketUnlockedEvent(command.getTicketId()));
		}
	}
	
	@EventSourcingHandler
	public void on(TicketCreatedEvent event) {
		
		this.ticketId = event.getTickeId();
		this.name = event.getName();
		
		LOG.info("Excuted event : {}", event);
	}
	
	@EventSourcingHandler
	public void on(OrderTicketPreservedEvent event) {
		
		this.lockUser = event.getCustomerId();		
		LOG.info("Excuted event : {}", event);
	}
	
	//交票
	@EventSourcingHandler
	public void on(OrderTicketMovedEvent event) {
		
		this.lockUser = null;		
		this.owner = event.getCustomerId();
		LOG.info("Excuted event : {}", event);
	}
	
	//交票
	@EventSourcingHandler
	public void on(OrderTicketUnlockedEvent event) {
		
		this.lockUser = null;		
		LOG.info("Excuted event : {}", event);
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLockUser() {
		return lockUser;
	}

	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	

}
