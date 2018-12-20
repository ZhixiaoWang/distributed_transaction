package com.imooc.example.ticket.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by mavlarn on 2018/1/26.
 */
@EnableJms
@Configuration
public class JmsConfig {
	
	//同步事务
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://101.37.146.151:61616");
		TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
		proxy.setTargetConnectionFactory(cf);
		proxy.setSynchedLocalTransactionAllowed(true);
		return cf;
	}
	
	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsConverter) {
		
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsConverter);
		jmsTemplate.setSessionTransacted(true);
		return jmsTemplate;
		
	}
	
	@Bean
	public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory cf,
			PlatformTransactionManager platformTransactionManager,
			DefaultJmsListenerContainerFactoryConfigurer configure) {
		
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configure.configure(factory, cf);
		factory.setReceiveTimeout(10000L);
		factory.setTransactionManager(platformTransactionManager);	
		factory.setConcurrency("10");
		
		return factory;
	}
	
	@Bean
	public MessageConverter jacksonMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		
		return converter;
	}
	
	
}
