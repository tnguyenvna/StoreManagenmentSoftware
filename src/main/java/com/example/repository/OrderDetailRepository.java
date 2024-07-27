package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.OrderDetail;

import jakarta.transaction.Transactional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM OrderDetail od WHERE od.order.id = :orderId")
	void deleteOrderDetailByOrderId(@Param("orderId") int orderId);
}
