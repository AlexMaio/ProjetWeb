package com.projetweb.spring.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projetweb.spring.security.model.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

	public List<Order> findAll();
	
	public Order findById(int id);
	
	@Query(nativeQuery = true,
            value = "SELECT Max(id) FROM order_table")
	public int idLastOrder();
	
	@SuppressWarnings("unchecked")
	public Order save(Order order);
	
}
