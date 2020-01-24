package com.java.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.entity.OrderEntity;
import com.java.repository.OrderRepository;

@Repository
@Transactional(value = "transactionManager", rollbackFor = Exception.class)
public class MySqlSerivce {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public List<OrderEntity> jdbcTemplateTest() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ORDERS ");
		List<OrderEntity> list = jdbcTemplate.query(sql.toString(), new Object[] {}, new BeanPropertyRowMapper<OrderEntity>(OrderEntity.class));
		return list;
	}
	
	public List<OrderEntity> repositoryTest() {
		return orderRepository.findAll();
	}
	
	public void saveEntity() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setOrderNumber(new DateTime().toString("YYYY-MM-DD HH:mm:ss"));
		orderRepository.save(orderEntity);
	}
	
}
