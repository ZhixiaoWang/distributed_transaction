package com.infosys.axon.order.query;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tb_order")
public class OrderEntity {
	
	@Id
	private String orderId;
	
	private String title;
	
	private String tickeId;
	
	private String customerId;
	
	private Double amount;
	
	private String reason;
	
	private String status;
	
	private ZonedDateTime createdDate;

	public OrderEntity() {

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

	@Override
	public String toString() {
		return "OrderEntity [orderId=" + orderId + ", title=" + title + ", tickeId=" + tickeId + ", customerId="
				+ customerId + ", amount=" + amount + ", reason=" + reason + ", createdDate=" + createdDate + "]";
	}
	
	

}
