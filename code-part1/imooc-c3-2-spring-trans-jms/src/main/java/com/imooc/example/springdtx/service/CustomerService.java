package com.imooc.example.springdtx.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by mavlarn on 2018/1/24.
 */
@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostConstruct
    public void init() {
        jmsTemplate.setReceiveTimeout(3000);
    }

    //此方法为使用jms session原生事务
//    @JmsListener(destination = "customer:msg1:new")
//    public void handle(String msg) {
//        LOG.debug("Get JMS message to from customer:{}", msg);
//        String reply = "Replied - " + msg;
//        jmsTemplate.convertAndSend("customer:msg:reply", reply);
//        if (msg.contains("error")) {
//            simulateError();
//        }
//    }
//    
    //此方法为使用jms事务管理，标签方式
    @JmsListener(destination = "customer:msg1:new", containerFactory = "msgFactory")
    //光使用@JmsListener，在直接调用时没有事务
    @Transactional //使用此标签，就可以使此方法在直接调用的情况下产生事务。
    public void handle(String msg) {
        LOG.debug("Get JMS message to from customer:{}", msg);
        String reply = "Replied - " + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }

    //此方法为使用JMS事务管理，代码方式
    @JmsListener(destination = "customer:msg2:new", containerFactory = "msgFactory")
    public void handle2(String msg) {
        LOG.debug("Get JMS message2 to from customer:{}", msg);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setTimeout(15);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String reply = "Replied-2 - " + msg;
            jmsTemplate.convertAndSend("customer:msg:reply", reply);
            if (!msg.contains("error")) {
                transactionManager.commit(status);
            } else {
                transactionManager.rollback(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
