package com.infosys.axon.order.event;

public class OrderFinishedEvent {
	
	private String orderId;
	
	

	public OrderFinishedEvent() {

	}

	public OrderFinishedEvent(String orderId) {
		super();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
