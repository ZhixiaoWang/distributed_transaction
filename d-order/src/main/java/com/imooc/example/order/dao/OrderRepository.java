package com.imooc.example.order.dao;

import com.imooc.example.order.domain.Order;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/1/20.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);
    
    List<Order> findAllByStatusAndCreatedDateBefore(String status, ZonedDateTime checkTime);
    
    Order findOneByUuid(String uuid);
}
