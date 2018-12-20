package com.infosys.axon.ticket.event;

public class OrderTicketPreserveFailedEvent {
	
	private String orderId;
	

	public OrderTicketPreserveFailedEvent() {

	}


	public OrderTicketPreserveFailedEvent(String orderId) {
		this.orderId = orderId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


}
