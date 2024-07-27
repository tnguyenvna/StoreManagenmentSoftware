package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findAllByIsActiveTrue();

	Optional<Product> findByIdAndIsActiveTrue(int id);
}
