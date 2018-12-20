package com.infosys.axon.order.query;

import org.axonframework.common.Assert;;

public class OrderId {
	
	private final String identifier;
	
	private final int hashCode;

	public OrderId(String identifier) {
		
		Assert.notNull(identifier, ()->"Identifier may not be null");
		this.identifier = identifier;
		this.hashCode = identifier.hashCode();
	}
	
	
	

}
