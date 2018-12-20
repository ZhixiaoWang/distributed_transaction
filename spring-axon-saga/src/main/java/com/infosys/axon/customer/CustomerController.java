package com.infosys.axon.customer;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.axon.customer.command.CustomerCreateCommand;
import com.infosys.axon.customer.command.CustomerDepositCommand;
import com.infosys.axon.customer.query.CustomerEntity;
import com.infosys.axon.customer.query.CustomerEntityRepository;
import com.infosys.axon.customer.command.CustomerChargeCommand;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private CustomerEntityRepository accountEntityRepository;
	
	@PostMapping("")
	public CompletableFuture<Object> create(@RequestParam String name, @RequestParam String password) {
		UUID customerId = UUID.randomUUID();
		CustomerCreateCommand command = new CustomerCreateCommand(customerId.toString(), name, password);
		return commandGateway.send(command);
	}
	
	@PutMapping("/{customerId}/deposit/{amount}")
	public void deposit(@PathVariable String customerId, @PathVariable Double amount) {
		commandGateway.send(new CustomerDepositCommand(customerId, amount));
	}
	
	@PutMapping("/{customerId}/withdraw/{amount}")
	public void withdraw(@PathVariable String customerId, @PathVariable Double amount) {
		commandGateway.send(new CustomerChargeCommand(customerId, amount));
	}
	
	@GetMapping("")
	public List<CustomerEntity> all() {
		return accountEntityRepository.findAll();
	}
	

}
