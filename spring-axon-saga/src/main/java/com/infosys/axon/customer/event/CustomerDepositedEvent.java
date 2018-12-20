package com.infosys.axon.customer.event;

public class CustomerDepositedEvent {
	
	private String accountId;
	
	private Double amount;
	

	public CustomerDepositedEvent() {
		super();
	}


	public CustomerDepositedEvent(String accountId, Double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
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
	
	
}
