package com.infosys.axon.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AccountDepositCommand {
	
	@TargetAggregateIdentifier
	private String accountId;
	
	private Double amount;

	public AccountDepositCommand(String accountId, Double amount) {
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
