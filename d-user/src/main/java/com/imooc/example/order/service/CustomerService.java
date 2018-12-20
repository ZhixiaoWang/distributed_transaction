package com.imooc.example.order.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.example.dto.OrderDTO;
import com.imooc.example.order.dao.CustomerRepository;
import com.imooc.example.order.dao.PayInfoRepository;
import com.imooc.example.order.domain.Customer;
import com.imooc.example.order.domain.PayInfo;


@Service
public class CustomerService {
	
private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PayInfoRepository payInfoRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;	

	@Transactional
	@JmsListener(destination = "order:pay", containerFactory = "msgFactory")
	public void handleOrderPay(OrderDTO order) {
		
		LOG.info("Get new order for pay: {}", order);
		
		//此处代码为解决幂等性，判断是否是重复消息
		PayInfo pay = payInfoRepository.findOneByOrderId(order.getId());
		Customer customer = customerRepository.findOne(order.getCustomerId());
		if(pay != null) {
			LOG.warn("Order already paid.");
			return;
		} else {
			
			if(customer.getDeposit() < order.getAmount()) {
				LOG.warn("Not enough deposit");
				order.setStatus("NOT_ENOUGH_DEPOSITE");
				jmsTemplate.convertAndSend("order:ticket_error", order);
				return;
			}

			pay = new PayInfo();
			pay.setStatus("PAID");
			pay.setOrderId(order.getId());
			pay.setAmount(order.getAmount());
			payInfoRepository.save(pay);
			
		}
		

		order.setStatus("PAID");
		jmsTemplate.convertAndSend("order:ticket_move", order);
		
		
//	这种方法做的话在高并发下会出现问题，所以要用数据库锁的方式来处理	
//		customer.setDeposit(customer.getDeposit() - order.getAmount());
//		customerRepository.save(customer);
		
		//此方法可在高并发下使用
		customerRepository.Charge(customer.getId(), order.getAmount());
	
	}

}
