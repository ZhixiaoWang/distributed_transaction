package com.infosys.axon.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AccountCreateCommand {
	
	@TargetAggregateIdentifier
	private String accountId;

	public AccountCreateCommand(String accountId) {
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
