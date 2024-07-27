package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByCustomerId(int customerId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Order o WHERE o.customer.id = :customerId")
	void deleteOrderByCustomerId(@Param("customerId") int customerId);

}
