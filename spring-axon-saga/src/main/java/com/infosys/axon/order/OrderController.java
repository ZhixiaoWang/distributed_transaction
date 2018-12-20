package com.infosys.axon.order;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.axon.order.command.OrderCreateCommand;
import com.infosys.axon.order.query.OrderEntity;
import com.infosys.axon.order.query.OrderEntityRepository;
import com.infosys.axon.order.query.OrderId;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private QueryGateway queryGateway;
	
	@Autowired
	private OrderEntityRepository orderEntityRepository;
	
	//LoggingCallback记日志用
	@PostMapping("")
	public void create(@RequestBody Order order) {
		UUID orderId = UUID.randomUUID();
		OrderCreateCommand command = new OrderCreateCommand
				(orderId.toString(),order.getTitle(), order.getTickeId(), order.getCustomerId(), order.getAmount());
		commandGateway.send(command, LoggingCallback.INSTANCE);
	}
	
//	@PutMapping("/{accountId}/deposit/{amount}")
//	public void deposit(@PathVariable String accountId, @PathVariable Double amount) {
//		commandGateway.send(new CustomerDepositCommand(accountId, amount));
//	}
//	
//	@PutMapping("/{accountId}/withdraw/{amount}")
//	public void withdraw(@PathVariable String accountId, @PathVariable Double amount) {
//		commandGateway.send(new CustomerChargeCommand(accountId, amount));
//	}
	
	@GetMapping("/{odrderId}")
	public OrderEntity getView(@PathVariable String orderId){
		return orderEntityRepository.findOne(orderId);
	}
	
	@GetMapping("/query/{odrderId}")
	public CompletableFuture<Order> getFromRep(@PathVariable String orderId){
		return queryGateway.query(new OrderId(orderId), Order.class);
	}
	

}
