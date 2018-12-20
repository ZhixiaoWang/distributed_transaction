package com.infosys.axon.query;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tb_account")
public class AccountEntity {
	
	@Id
	private String accountId;
	
	private Double deposite;
	
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Double getDeposite() {
		return deposite;
	}

	public void setDeposite(Double deposite) {
		this.deposite = deposite;
	}
	
	

}
