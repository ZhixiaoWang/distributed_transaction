package com.infosys.axon.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AccountWithdrawCommand {
	
	@TargetAggregateIdentifier
	private String accountId;
	
	private Double amount;

	public AccountWithdrawCommand(String accountId, Double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

}
