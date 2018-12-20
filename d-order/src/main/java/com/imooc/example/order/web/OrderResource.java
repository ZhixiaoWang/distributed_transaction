package com.imooc.example.order.web;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.imooc.example.IOrderService;
import com.imooc.example.dto.OrderDTO;
import com.imooc.example.order.dao.OrderRepository;
import com.imooc.example.order.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by mavlarn on 2018/1/20.
 */
@RestController
@RequestMapping("/api/order")
public class OrderResource implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    private TimeBasedGenerator uuidGenerator = Generators.timeBasedGenerator();

    @PostMapping("")
    public void create(@RequestBody OrderDTO order) {
    	order.setUuid(uuidGenerator.generate().toString());
        jmsTemplate.convertAndSend("order:new", order);
    }

    @GetMapping("/{id}")
    public OrderDTO getMyOrder(@PathVariable Long id) {
        Order order = orderRepository.findOne(id);
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setAmount(order.getAmount());
        dto.setTitle(order.getTitle());
//        dto.setDetail(order.getDetail());
        return dto;
    }

    @GetMapping("")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}
