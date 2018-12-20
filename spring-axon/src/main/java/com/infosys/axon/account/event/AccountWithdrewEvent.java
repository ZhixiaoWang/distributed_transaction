package com.infosys.axon.account.event;

public class AccountWithdrewEvent {
	
	private String accountId;
	
	private Double amount;

	public AccountWithdrewEvent(String accountId, Double amount) {
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
