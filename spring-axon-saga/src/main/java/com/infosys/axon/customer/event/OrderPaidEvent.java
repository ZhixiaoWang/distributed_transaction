package com.infosys.axon.customer.event;

public class OrderPaidEvent {
	
	private String accountId;
	
	private String orderId;
	
	private Double amount;
	

	public OrderPaidEvent() {
		super();
	}


	public OrderPaidEvent(String accountId, String orderId, Double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
		this.orderId = orderId;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
}
