package com.infosys.axon.ticket.event;

public class OrderTicketPreservedEvent {
	
	private String ticketId;
	
	private String orderId;
	
	private String customerId;
	
	

	public OrderTicketPreservedEvent() {

	}

	public OrderTicketPreservedEvent(String ticketId, String orderId, String customerId) {
		this.ticketId = ticketId;
		this.orderId = orderId;
		this.customerId = customerId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


}
