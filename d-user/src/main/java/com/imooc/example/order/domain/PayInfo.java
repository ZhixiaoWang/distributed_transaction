package com.imooc.example.order.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PayInfo {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long orderId;
	
	private String status;
	
	private int amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PayInfo [id=" + id + ", orderId=" + orderId + ", status=" + status + ", amount=" + amount + "]";
	}
	

}
