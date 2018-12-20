package com.infosys.axon.customer.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.axon.customer.event.CustomerCreatedEvent;
import com.infosys.axon.customer.event.CustomerDepositedEvent;
import com.infosys.axon.customer.event.OrderPaidEvent;
import com.infosys.axon.customer.event.CustomerChargeEvent;

@Component
public class CustomerProjector {
	
	@Autowired
	private CustomerEntityRepository repository;
	
	@EventHandler
	public void on(CustomerCreatedEvent event) {
		
		CustomerEntity customer = new CustomerEntity();
		customer.setCustomerId(event.getCustomerId());
		customer.setUsername(event.getUsername());
		customer.setPassword(event.getPassword());
		repository.save(customer);
		
	}
	
	@EventHandler
	public void on(CustomerDepositedEvent event) {
		String customerId = event.getAccountId();
		CustomerEntity accountView = repository.findOne(customerId);
		Double newDeposit = accountView.getDeposite() + event.getAmount();
		accountView.setDeposite(newDeposit);
		repository.save(accountView);
	}
	
	@EventHandler
	public void on(CustomerChargeEvent event) {
		
		CustomerEntity accountView = repository.findOne(event.getAccountId());
		accountView.setDeposite(accountView.getDeposite() - event.getAmount());
		repository.save(accountView);
		
	}
	
	@EventHandler
	public void on(OrderPaidEvent event) {
		
		CustomerEntity accountView = repository.findOne(event.getAccountId());
		accountView.setDeposite(accountView.getDeposite() - event.getAmount());
		repository.save(accountView);
		
	}

}
