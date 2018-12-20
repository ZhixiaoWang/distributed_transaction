package com.infosys.axon.order.command;


import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderCreateCommand {
	
	@TargetAggregateIdentifier
	private String orderId;
	
	private String title;
	
	private String tickeId;
	
	private String customerId;
	
	private Double amount;

	public OrderCreateCommand(String orderId, String title, String tickeId, String customerId, Double amount) {
		super();
		this.orderId = orderId;
		this.title = title;
		this.tickeId = tickeId;
		this.customerId = customerId;
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTickeId() {
		return tickeId;
	}

	public void setTickeId(String tickeId) {
		this.tickeId = tickeId;
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
	

}
