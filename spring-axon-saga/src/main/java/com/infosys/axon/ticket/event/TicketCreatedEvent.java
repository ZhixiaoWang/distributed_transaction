package com.infosys.axon.ticket.event;

public class TicketCreatedEvent {
	
	private String tickeId;
	
	private String name;
	
	
	public TicketCreatedEvent() {

	}

	public TicketCreatedEvent(String tickeId, String name) {
		this.tickeId = tickeId;
		this.name = name;
	}

	public String getTickeId() {
		return tickeId;
	}

	public void setTickeId(String tickeId) {
		this.tickeId = tickeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
