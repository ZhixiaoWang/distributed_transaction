package com.infosys.axon.customer.event;

public class CustomerCreatedEvent {
	
	private String customerId;
	
	private String username;
	
	private String password;
	

	public CustomerCreatedEvent() {
		super();
	}

	public CustomerCreatedEvent(String customerId, String username, String password) {
		this.customerId = customerId;
		this.username = username;
		this.password = password;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
