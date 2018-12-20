package com.infosys.axon.account.event;

public class AccountCreatedEvent {
	
	private String accountId;

	public AccountCreatedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	

}
