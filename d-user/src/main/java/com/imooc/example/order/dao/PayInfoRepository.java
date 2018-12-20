package com.imooc.example.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.imooc.example.order.domain.PayInfo;

public interface PayInfoRepository extends JpaRepository<PayInfo, Long>{
	
	PayInfo findOneByOrderId(Long orderId);
	
    @Modifying(clearAutomatically=true)
    PayInfo save(PayInfo pay);
    

}
