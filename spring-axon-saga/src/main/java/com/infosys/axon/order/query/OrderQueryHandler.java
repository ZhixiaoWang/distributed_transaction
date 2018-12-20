package com.infosys.axon.order.query;


import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infosys.axon.order.Order;

//此类只在测试时使用，只为了看聚合对象的数据是否有问题
@Component
public class OrderQueryHandler {

	@Autowired
	private AxonConfiguration axonConfiguration;
	
	@QueryHandler
	public Order query(OrderId orderId) {
		
		final Order[] orders = new Order[1];
		Repository<Order> repository = axonConfiguration.repository(Order.class);
		repository.load(orderId.toString()).execute(order -> {
			orders[0] = order;
		});
		return orders[0];
		
	}
	
}
