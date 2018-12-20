package com.infosys.axon.customer;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import com.infosys.axon.customer.command.CustomerCreateCommand;
import com.infosys.axon.customer.command.CustomerDepositCommand;
import com.infosys.axon.customer.command.OrderPayCommand;
import com.infosys.axon.customer.command.CustomerChargeCommand;
import com.infosys.axon.customer.event.CustomerCreatedEvent;
import com.infosys.axon.customer.event.CustomerDepositedEvent;
import com.infosys.axon.customer.event.OrderPaidEvent;
import com.infosys.axon.customer.event.CustomerChargeEvent;


//加此注解表示是领域模型
@Aggregate
public class Customer {
	
	//表示是领域模型id
	@AggregateIdentifier
	private String customerId;
	
	private String username;
	
	private String password;
	
	private Double deposite;
	
	
	public Customer() {}

	@CommandHandler
	public Customer(CustomerCreateCommand command) {
		apply(new CustomerCreatedEvent(command.getCustomerId(), command.getUsername(), command.getPassword()));
	}
	
	@CommandHandler
	public void handle(CustomerDepositCommand command) {
		apply(new CustomerDepositedEvent(command.getCustomerId(), command.getAmount()));
	}
	
	@CommandHandler
	public void handle(CustomerChargeCommand command) {
		if(this.getDeposite() - command.getAmount() < 0) {
			apply(new CustomerChargeEvent(command.getCustomerId(), command.getAmount()));
		}else {
			throw new IllegalArgumentException("余额不足");
		}
	}
	
	@CommandHandler
	public void handle(OrderPayCommand command) {
		if(command.getAmount() == 0 ) {
			//do nothing,此时不往下走，就会30秒超时
		}else if(this.getDeposite() - command.getAmount() >= 0) {
			apply(new OrderPaidEvent(command.getCustomerId(), command.getOrderId(), command.getAmount()));
		}else {
			throw new IllegalArgumentException("余额不足");
		}
	}


	@EventSourcingHandler
	public void on(CustomerCreatedEvent event) {
		this.customerId = event.getCustomerId();
		this.deposite = 0d;
	}
	
	@EventSourcingHandler
	public void on(CustomerDepositedEvent event) {
		this.deposite += event.getAmount();
	}
	
	@EventSourcingHandler
	public void on(CustomerChargeEvent event) {
		this.deposite -= event.getAmount();
	}
	
	@EventSourcingHandler
	public void on(OrderPaidEvent event) {
		this.deposite -= event.getAmount();
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getDeposite() {
		return deposite;
	}

	public void setDeposite(Double deposite) {
		this.deposite = deposite;
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
