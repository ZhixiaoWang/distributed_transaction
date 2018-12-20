package com.imooc.springdtx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.springdtx.domain.Order;

@Service
public class CustomerService {
	
	@Autowired
	@Qualifier("userJdbcTemplate")
	private JdbcTemplate userJdbcTemplate;
	
	@Autowired
	@Qualifier("orderJdbcTemplate")
	private JdbcTemplate orderJdbcTemplate;
	
    private static final String SQL_UPDATE_DEPOSITE = "UPDATE customer SET deposit = deposit - ? where id =? ";
    private static final String SQL_CREATE_ORDER = "INSERT INTO customer_order(customer_id, title, amount) VALUES (?, ?, ?) ";
    
    @Transactional
    public void createOrder(Order order) {
    	userJdbcTemplate.update(SQL_UPDATE_DEPOSITE, order.getAmount(), order.getCustomerId());
    	if(order.getTitle().contains("error1")) {
    		throw new RuntimeException("Error1");
    	}
    	orderJdbcTemplate.update(SQL_CREATE_ORDER, order.getCustomerId(), order.getTitle(), order.getAmount());
    	if(order.getTitle().contains("error2")) {
    		throw new RuntimeException("Error2");
    	}
    }
    
    public Map userInfo(Long customerId) {
    	Map customer = userJdbcTemplate.queryForMap("select * from customer where id = " + customerId);
    	List orders = orderJdbcTemplate.queryForList("select * from customer_order where customer_id = " + customerId);
    	
    	Map result = new HashMap();
    	result.put("customer", customer);
    	result.put("orders", orders);
    	
    	return result;
    }
	
}
