package com.infosys.axon.order.event;

public class OrderFailedEvent {
	
	private String orderId;
	
	private String reason;
	
	

	public OrderFailedEvent() {

	}

	public OrderFailedEvent(String orderId, String reason) {
		super();
		this.orderId = orderId;
		this.reason = reason;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
