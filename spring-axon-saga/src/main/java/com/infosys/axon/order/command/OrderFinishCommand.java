package com.infosys.axon.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderFinishCommand {
	
	@TargetAggregateIdentifier
	private String orderId;

	public OrderFinishCommand(String orderId) {
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
