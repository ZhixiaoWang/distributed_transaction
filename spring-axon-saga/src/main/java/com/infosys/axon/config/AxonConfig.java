package com.infosys.axon.config;

import java.util.concurrent.Executors;

import org.axonframework.common.transaction.TransactionManager;

import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.scheduling.EventScheduler;
import org.axonframework.eventhandling.scheduling.java.SimpleEventScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
	
	@Bean
	public EventScheduler eventScheduler(EventBus eventBus, TransactionManager transactionManager) {
		//SimpleEventSecheduler只是内存的方式去实现，详见方法注释
		return new SimpleEventScheduler(Executors.newScheduledThreadPool(1), eventBus, transactionManager);
	}

}
