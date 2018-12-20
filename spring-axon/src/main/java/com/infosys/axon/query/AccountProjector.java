package com.infosys.axon.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.axon.account.event.AccountCreatedEvent;
import com.infosys.axon.account.event.AccountDepositedEvent;
import com.infosys.axon.account.event.AccountWithdrewEvent;

@Component
public class AccountProjector {
	
	@Autowired
	private AccountEntityRepository accountEntityRepository;
	
	@EventHandler
	public void on(AccountCreatedEvent event) {
		
		AccountEntity account = new AccountEntity();
		account.setAccountId(event.getAccountId());
		accountEntityRepository.save(account);
		
	}
	
	@EventHandler
	public void on(AccountDepositedEvent event) {
		
		AccountEntity account = accountEntityRepository.findOne(event.getAccountId());
		account.setDeposite(account.getDeposite() + event.getAmount());
		accountEntityRepository.save(account);
		
	}
	
	@EventHandler
	public void on(AccountWithdrewEvent event) {
		
		AccountEntity account = accountEntityRepository.findOne(event.getAccountId());
		account.setDeposite(account.getDeposite() - event.getAmount());
		accountEntityRepository.save(account);
		
	}

}
