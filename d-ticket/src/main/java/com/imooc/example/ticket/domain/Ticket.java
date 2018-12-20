package com.imooc.example.ticket.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by mavlarn on 2018/1/20.
 */
@Entity(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long owner;
    
    private Long lockUser;

    private long ticketNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Long getLockUser() {
		return lockUser;
	}

	public void setLockUser(Long lockUser) {
		this.lockUser = lockUser;
	}

	public long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(long ticketNum) {
		this.ticketNum = ticketNum;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", name=" + name + ", owner=" + owner + ", lockUser=" + lockUser + ", ticketNum="
				+ ticketNum + "]";
	}


}
