package com.infosys.axon.order.query;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.axon.order.event.OrderCreatedEvent;
import com.infosys.axon.order.event.OrderFailedEvent;
import com.infosys.axon.order.event.OrderFinishedEvent;

@Component
public class OrderProjector {
	
	@Autowired
	OrderEntityRepository orderEntityRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderProjector.class);
	
	@EventHandler
	public void on(OrderCreatedEvent event) {
		OrderEntity order = new OrderEntity();
		order.setOrderId(event.getOrderId());
		order.setAmount(event.getAmount());
		order.setTickeId(event.getTickeId());
		order.setTitle(event.getTitle());
		order.setCustomerId(event.getCustomerId());
	    order.setCreatedDate(event.getCreatedDate());
	    order.setStatus("NEW");
	    orderEntityRepository.save(order);

		LOG.info("Excuted Event : {}", event);
		
	}
	
	@EventHandler
	public void on(OrderFinishedEvent event) {
		OrderEntity order = orderEntityRepository.findOne(event.getOrderId());
		order.setStatus("FINISHED");

		LOG.info("Excuted Event : {}", event);
	}
	
	@EventHandler
	public void on(OrderFailedEvent event) {
		OrderEntity order = orderEntityRepository.findOne(event.getOrderId());
		order.setStatus("FAILED");
		order.setReason(event.getReason());
		LOG.info("Excuted Event : {}", event);
	}

}
