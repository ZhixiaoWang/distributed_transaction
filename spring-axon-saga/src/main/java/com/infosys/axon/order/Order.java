package com.infosys.axon.order;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.time.ZonedDateTime;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infosys.axon.order.command.OrderCreateCommand;
import com.infosys.axon.order.command.OrderFailCommand;
import com.infosys.axon.order.command.OrderFinishCommand;
import com.infosys.axon.order.event.OrderCreatedEvent;
import com.infosys.axon.order.event.OrderFailedEvent;
import com.infosys.axon.order.event.OrderFinishedEvent;



@Aggregate
public class Order {
	
	private static final Logger LOG = LoggerFactory.getLogger(Order.class);
	
	@AggregateIdentifier
	private String orderId;
	
	private String title;
	
	private String tickeId;
	
	private String customerId;
	
	private Double amount;
	
	private String status;
	
	private String reason;
	
	private ZonedDateTime createdDate;

	//需要配置没有任何参数的构造函数
	public Order() {

	}
	
	@CommandHandler
	public Order(OrderCreateCommand command) {
		apply(new OrderCreatedEvent(command.getOrderId(),command.getTitle(),
				command.getTickeId(),command.getCustomerId(),command.getAmount(),ZonedDateTime.now()));
	}
	
	@CommandHandler
	public void handle(OrderFinishCommand command) {
		apply(new OrderFinishedEvent(command.getOrderId()));
	}
	
	@CommandHandler
	public void handle(OrderFailCommand command) {
		apply(new OrderFailedEvent(command.getOrderId(), command.getReason()));
	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent event) {
		this.orderId = event.getOrderId();
		this.tickeId = event.getTickeId();
		this.customerId = event.getCustomerId();
		this.title = event.getTitle();
		this.amount = event.getAmount();
		this.status = "NEW";
		this.createdDate = event.getCreatedDate();
		
		LOG.info("Excuted Event : {}", event);
		
	}
	
	@EventSourcingHandler
	public void on(OrderFinishedEvent event) {
		this.status = "FINISHED";
		LOG.info("Excuted Event : {}", event);
	}
	
	@EventSourcingHandler
	public void on(OrderFailedEvent event) {
		this.status = "FAILED";
		this.reason = event.getReason();
		LOG.info("Excuted Event : {}", event);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTickeId() {
		return tickeId;
	}

	public void setTickeId(String tickeId) {
		this.tickeId = tickeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
