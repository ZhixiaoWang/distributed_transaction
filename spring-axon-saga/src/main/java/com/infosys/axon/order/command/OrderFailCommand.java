package com.infosys.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderFailCommand {
	
	@TargetAggregateIdentifier
	private String orderId;
	
	private String reason;

	public OrderFailCommand(String orderId, String reason) {
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
