package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Payment;

import jakarta.transaction.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM Payment p WHERE p.order.id = :orderId")
	void deletePaymentsByOrderId(@Param("orderId") int orderId);
	
	
}
