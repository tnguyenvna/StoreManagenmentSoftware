package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Order;
import com.example.entity.OrderDetail;
import com.example.entity.Product;
import com.example.repository.OrderRepository;

@Service
public class ReportService {
	@Autowired
	private OrderRepository orderRepository;

	public BigDecimal getTotalRevenue() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().flatMap(order -> order.getOrderDetails().stream()).map(OrderDetail::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Product> getBestSellingproducts() {
		return null;
	}
}
