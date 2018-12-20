package com.imooc.springdtx.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.springdtx.domain.Order;
import com.imooc.springdtx.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/order")
	public void create(@RequestBody Order order) {
		customerService.createOrder(order);
	}
	
	@GetMapping("/{id}")
	public Map userInfo(@PathVariable Long id) {
		return customerService.userInfo(id);
	}

}
