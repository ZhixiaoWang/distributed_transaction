package com.imooc.springdtx.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.ds_user")
	public DataSourceProperties userDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	public DataSource userDataSource() {
		return userDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Bean
	public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource userDataSource) {
		return new JdbcTemplate(userDataSource);
	}
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.ds_order")
	public DataSourceProperties orderDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	public DataSource orderDataSource() {
		return orderDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
	@Bean
	public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource) {
		return new JdbcTemplate(orderDataSource);
	}
	
	//此配置文件里加上链式事务，就可以实现类似2阶段提交了。但如果第一个事务提交成功，提交第二个事务时，服务器断掉，则数据会不一致。
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager userTM = new DataSourceTransactionManager(userDataSource()); //代表从容器中获取userDataSource，而不是方法的调用
		DataSourceTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource());
	    //创建链式事务
		ChainedTransactionManager tm = new ChainedTransactionManager(userTM, orderTM);
		return tm;
	}
	

}
