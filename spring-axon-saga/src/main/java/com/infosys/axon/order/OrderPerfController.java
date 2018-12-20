package com.infosys.axon.order;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.mockito.internal.stubbing.answers.ReturnsElementsOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.axon.customer.query.CustomerEntity;
import com.infosys.axon.customer.query.CustomerEntityRepository;
import com.infosys.axon.order.command.OrderCreateCommand;
import com.infosys.axon.ticket.query.TicketEntity;
import com.infosys.axon.ticket.query.TicketEntityRepository;


//并发流程
@RestController
@RequestMapping("/orders")
public class OrderPerfController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderPerfController.class);
	
	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private QueryGateway queryGateway;
	
	@Autowired
	private TicketEntityRepository ticketEntityRepository;
	
	@Autowired
	private CustomerEntityRepository customerEntityRepository;
	
	private List<TicketEntity> ticketList;
	private List<CustomerEntity> customerList;
	
//	@PostMapping("")
//	public void create(@RequestBody Order order) {
//		UUID orderId = UUID.randomUUID();
//		OrderCreateCommand command = new OrderCreateCommand
//				(orderId.toString(),order.getTitle(), order.getTickeId(), order.getCustomerId(), order.getAmount());
//		commandGateway.send(command, LoggingCallback.INSTANCE);
//	}
	
	@PostMapping("/test/oneUserAllTicket")
	public void oneUserAllTicket(@RequestBody Order order) {
		Random random = new Random();
		TicketEntity buyTicket = this.ticketList.get(random.nextInt(ticketList.size()));
		UUID orderId = UUID.randomUUID();
		OrderCreateCommand command = new OrderCreateCommand
				(orderId.toString(),order.getTitle(), order.getTickeId(), order.getCustomerId(), order.getAmount());
		LOG.info("OneUserAllTicket Create Order: {} ", command);
		commandGateway.send(command, LoggingCallback.INSTANCE);
	}
	
	@PostMapping("/test/allUserOneTicket")
	public void allUserOneTicket(@RequestBody Order order) {
		Random random = new Random();
		CustomerEntity customer = this.customerList.get(random.nextInt(ticketList.size()));
		UUID orderId = UUID.randomUUID();
		OrderCreateCommand command = new OrderCreateCommand
				(orderId.toString(),order.getTitle(), order.getTickeId(), order.getCustomerId(), order.getAmount());
		LOG.info("AllUserOneTicket Create Order: {} ", command);
		commandGateway.send(command, LoggingCallback.INSTANCE);
	}
	
	@GetMapping("/tickets")
	public List<TicketEntity> getAllTickets(){
		List<TicketEntity> ticketEntities = ticketEntityRepository.findAll();
		return this.ticketList;
	}
	
	@GetMapping("/customers")
	public List<CustomerEntity> getAllCustomers(){
		this.customerList = customerEntityRepository.findAll();
		return this.customerList;
	}


}
