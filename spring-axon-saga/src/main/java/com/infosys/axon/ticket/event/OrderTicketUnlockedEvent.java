package com.infosys.axon.ticket.event;

public class OrderTicketUnlockedEvent {
	
	private String ticketId;
	

	public OrderTicketUnlockedEvent() {

	}

	public OrderTicketUnlockedEvent(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}



}
