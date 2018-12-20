package com.infosys.axon.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderPayCommand {
	
	@TargetAggregateIdentifier
	private String customerId;
	
	private String orderId;
	
	private Double amount;

	public OrderPayCommand(String customerId, String orderId, Double amount) {
		super();
		this.customerId = customerId;
		this.amount = amount;
		this.orderId = orderId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
