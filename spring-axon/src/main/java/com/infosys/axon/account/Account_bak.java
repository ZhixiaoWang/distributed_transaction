//package com.infosys.axon.account;
//
//import org.axonframework.commandhandling.CommandHandler;
//import org.axonframework.commandhandling.model.AggregateIdentifier;
//import org.axonframework.eventsourcing.EventSourcingHandler;
//import org.axonframework.spring.stereotype.Aggregate;
//
//import com.infosys.axon.account.command.AccountCreateCommand;
//import com.infosys.axon.account.command.AccountDepositCommand;
//import com.infosys.axon.account.command.AccountWithdrawCommand;
//import com.infosys.axon.account.event.AccountCreatedEvent;
//import com.infosys.axon.account.event.AccountDepositedEvent;
//import com.infosys.axon.account.event.AccountWithdrewEvent;
//
//import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//
//
////加此注解表示是领域模型
//@Aggregate
//@Entity(name="tb_account")
////现在即是entity又是聚合对象，只在简单应用中这样使用
//public class Account_bak {
//	
//	//表示是领域模型id
//	//@AggregateIdentifier
//	@Id
//	private String accountId;
//	
//	private Double deposite;
//	
//	
//	public Account_bak() {}
//
//	@CommandHandler
//	public Account_bak(AccountCreateCommand command) {
//		apply(new AccountCreatedEvent(command.getAccountId()));
//	}
//	
//	@CommandHandler
//	public void handle(AccountDepositCommand command) {
//		apply(new AccountDepositedEvent(command.getAccountId(), command.getAmount()));
//	}
//	
//	@CommandHandler
//	public void handle(AccountWithdrawCommand command) {
//		if(this.getDeposite() >= command.getAmount()) {
//			apply(new AccountDepositedEvent(command.getAccountId(), command.getAmount()));
//		}else {
//			throw new IllegalArgumentException("余额不足");
//		}
//		
//	}
//
//	@EventSourcingHandler
//	public void on(AccountCreatedEvent event) {
//		this.accountId = event.getAccountId();
//		this.deposite = 0d;
//	}
//	
//	@EventSourcingHandler
//	public void on(AccountDepositedEvent event) {
//		this.deposite += event.getAmount();
//	}
//	
//	@EventSourcingHandler
//	public void on(AccountWithdrewEvent event) {
//		this.deposite -= event.getAmount();
//	}
//	
//	public String getAccountId() {
//		return accountId;
//	}
//
//	public void setAccountId(String accountId) {
//		this.accountId = accountId;
//	}
//
//	public Double getDeposite() {
//		return deposite;
//	}
//
//	public void setDeposite(Double deposite) {
//		this.deposite = deposite;
//	}
//	
//	
//
//}
