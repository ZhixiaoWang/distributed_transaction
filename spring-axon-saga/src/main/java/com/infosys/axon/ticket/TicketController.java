package com.infosys.axon.ticket;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.axon.ticket.command.TicketCreateCommand;
import com.infosys.axon.ticket.query.TicketEntity;
import com.infosys.axon.ticket.query.TicketEntityRepository;

@RestController
@RequestMapping("/tickets")
public class TicketController {
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private TicketEntityRepository ticketEntityRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);
	
	@PostMapping("")
	public CompletableFuture<Object> create(@RequestParam String name) {
		LOG.info("Request to create ticket: {}", name);
		UUID ticketId = UUID.randomUUID();
		TicketCreateCommand command = new TicketCreateCommand(ticketId.toString(),name);
		return commandGateway.send(command);
	}

	@GetMapping("")
	public List<TicketEntity> all() {
		return ticketEntityRepository.findAll();
	}
	

}
