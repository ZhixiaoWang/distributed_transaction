package com.infosys.axon.ticket.command;


import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderTicketUnlockCommand {
	
	@TargetAggregateIdentifier
	private String ticketId;
	
	private String customerId;

	public OrderTicketUnlockCommand(String ticketId, String customerId) {
		this.ticketId = ticketId;
		this.customerId = customerId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	

}
