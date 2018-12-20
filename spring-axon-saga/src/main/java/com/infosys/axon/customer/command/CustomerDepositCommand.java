package com.infosys.axon.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CustomerDepositCommand {
	
	@TargetAggregateIdentifier
	private String customerId;
	
	private Double amount;

	public CustomerDepositCommand(String customerId, Double amount) {
		super();
		this.customerId = customerId;
		this.amount = amount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setAccountId(String customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	

}
