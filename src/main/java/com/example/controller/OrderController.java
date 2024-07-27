package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Order;
import com.example.entity.OrderDetail;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderService orderService;

	@GetMapping
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable int id) {
		return orderService.getOrderById(id);
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		return orderService.createOrder(order);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateOrder(@PathVariable int id, @RequestBody Order orderDetails) {
		return orderService.updateOrder(id, orderDetails);
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable int id) {
		orderRepository.deleteById(id);
	}

	@GetMapping("/{id}/details")
	public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable int id) {
		Order order = orderRepository.findById(id).orElse(null);
		return order != null ? order.getOrderDetails() : null;
	}
}
