package com.projetweb.spring.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projetweb.spring.security.model.OrderDetail;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer> {
	
	List<OrderDetail> findById(int id);
	
	@Query(nativeQuery = true,
            value = "SELECT * FROM order_detail WHERE id_order = :id")
	public List<OrderDetail> listeIdProducts(@Param("id") int id);
	
	@SuppressWarnings("unchecked")
	public OrderDetail save(OrderDetail orderDetail);

}
