package com.infosys.axon.account;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.axon.account.command.AccountCreateCommand;
import com.infosys.axon.account.command.AccountDepositCommand;
import com.infosys.axon.account.command.AccountWithdrawCommand;
import com.infosys.axon.query.AccountEntity;
import com.infosys.axon.query.AccountEntityRepository;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private AccountEntityRepository accountEntityRepository;
	
	@PostMapping("")
	public CompletableFuture<Object> create() {
		UUID accountId = UUID.randomUUID();
		AccountCreateCommand command = new AccountCreateCommand(accountId.toString());
		return commandGateway.send(command);
	}
	
	@PutMapping("/{accountId}/deposit/{amount}")
	public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
		commandGateway.send(new AccountDepositCommand(accountId, amount));
	}
	
	@PutMapping("/{accountId}/withdraw/{amount}")
	public void withdraw(@PathVariable String accountId, @PathVariable Double amount) {
		commandGateway.send(new AccountWithdrawCommand(accountId, amount));
	}
	
	@GetMapping("")
	public List<AccountEntity> all() {
		return accountEntityRepository.findAll();
	}
	

}
