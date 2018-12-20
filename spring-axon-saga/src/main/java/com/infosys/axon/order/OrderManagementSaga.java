package com.infosys.axon.order;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import java.time.Instant;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.ScheduleToken;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infosys.axon.customer.command.OrderPayCommand;
import com.infosys.axon.customer.event.OrderPaidEvent;
import com.infosys.axon.customer.event.OrderPayFailedEvent;
import com.infosys.axon.order.command.OrderFailCommand;
import com.infosys.axon.order.command.OrderFinishCommand;
import com.infosys.axon.order.event.OrderCreatedEvent;
import com.infosys.axon.order.event.OrderFailedEvent;
import com.infosys.axon.order.event.OrderFinishedEvent;
import com.infosys.axon.ticket.command.OrderTicketMoveCommand;
import com.infosys.axon.ticket.command.OrderTicketPreserveCommand;
import com.infosys.axon.ticket.command.OrderTicketUnlockCommand;
import com.infosys.axon.ticket.event.OrderTicketMovedEvent;
import com.infosys.axon.ticket.event.OrderTicketPreserveFailedEvent;
import com.infosys.axon.ticket.event.OrderTicketPreservedEvent;

@Saga
public class OrderManagementSaga {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderManagementSaga.class);
	
	@Autowired
	private transient CommandBus commandBus;  //让此对象不要序列化
	
	@Autowired
	private transient EventScheduler eventScheduler;
	
	private String orderId;
	private String ticketId;
	private String customerId;
	private Double amount;
	
	private ScheduleToken timeoutToken;
	
	@StartSaga   //整个流程开始的地方
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderCreatedEvent event) {
		
		this.orderId = event.getOrderId();
		this.ticketId = event.getTickeId();
		this.customerId = event.getCustomerId();
		this.amount = event.getAmount();
		
		//测试超时，不测试时可以注释掉此代码。30秒之后生成order fail event
		timeoutToken = eventScheduler.schedule(Instant.now().plusSeconds(30), new OrderPayFailedEvent(orderId));
		
		OrderTicketPreserveCommand command = new OrderTicketPreserveCommand(ticketId, orderId, customerId);
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderTicketPreservedEvent event) {
		OrderPayCommand command = new OrderPayCommand(customerId, orderId, amount);
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderTicketPreserveFailedEvent event) {
		OrderFailCommand command = new OrderFailCommand(orderId, "Preserve fail");
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderPaidEvent event) {	
		OrderTicketMoveCommand command = new OrderTicketMoveCommand(ticketId, orderId, customerId);
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderPayFailedEvent event) {
		OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(ticketId, customerId);
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	    
	    OrderFailCommand failCommand = new OrderFailCommand(orderId, "Paid fail");
	    commandBus.dispatch(asCommandMessage(failCommand), LoggingCallback.INSTANCE);
	}
	
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderTicketMovedEvent event) {	
		OrderFinishCommand command = new OrderFinishCommand(orderId);
	    commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
	}
	
	
	@EndSaga
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderFailedEvent event) {
		LOG.info("Order: {} failed. ", event.getOrderId());
		if(this.timeoutToken != null) {
			eventScheduler.cancelSchedule(this.timeoutToken);
		}
	}

	@EndSaga
	@SagaEventHandler(associationProperty="orderId")
	public void on(OrderFinishedEvent event) {
		LOG.info("Order: {} finished. ", event.getOrderId());
		//如果正常处理了，则把超时schedule取消
		if(this.timeoutToken != null) {
			eventScheduler.cancelSchedule(this.timeoutToken);
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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
	
	
	
}
