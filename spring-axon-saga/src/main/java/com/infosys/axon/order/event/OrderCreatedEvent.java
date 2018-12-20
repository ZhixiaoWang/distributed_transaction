package com.infosys.axon.order.event;

import java.time.ZonedDateTime;


public class OrderCreatedEvent {
	
	private String orderId;
	
	private String title;
	
	private String tickeId;
	
	private String customerId;
	
	private Double amount;

	private ZonedDateTime createdDate;
	

	public OrderCreatedEvent() {

	}

	public OrderCreatedEvent(String orderId, String title, String tickeId, String customerId, Double amount,
			ZonedDateTime createdDate) {
		super();
		this.orderId = orderId;
		this.title = title;
		this.tickeId = tickeId;
		this.customerId = customerId;
		this.amount = amount;
		this.createdDate = createdDate;
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

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
