package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	List<Customer> findAllByIsActiveTrue();
	
	Optional<Customer> findByIdAndIsActiveTrue(int id);
}
