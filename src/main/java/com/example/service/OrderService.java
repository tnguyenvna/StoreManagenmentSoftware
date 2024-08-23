package com.example.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.OrderDetail;
import com.example.entity.Product;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderRepository;
import com.example.util.MessageUtil;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	public ResponseEntity<Order> getOrderById(int id) {
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(order);
	}

	@Transactional
	public ResponseEntity<Map<String, String>> updateOrder(int id, Order orderDetails) {
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null) {
			return MessageUtil.orderNotFoundResponse();
		}

		order.setStatus(orderDetails.getStatus());
		order.setOrder_date(orderDetails.getOrder_date());
		order.setCustomer(customerRepository.findById(orderDetails.getCustomer().getId()).orElse(null));

		orderRepository.save(order);
		return MessageUtil.orderUpdateSuccessfullyResponse();
	}

//	@Transactional
//	public ResponseEntity<Order> createOrder(Order order) {

		// Tải khách hàng từ DB
//		Customer customer = customerService.getCustomerById(order.getCustomer().getId()).getBody();
//		if (customer == null) {
//			return ResponseEntity.notFound().build();
//		}
//		order.setCustomer(customer);
//
//		// Cài đặt tham chiếu đến đơn hàng trong mỗi chi tiết đơn hàng
//		for (OrderDetail detail : order.getOrderDetails()) {
			// Tải sản phẩm từ DB
//			Product product = productService.getProductById(detail.getProduct().getId()).getBody();
//			if (product == null) {
//				return ResponseEntity.notFound().build();
//			}
//			detail.setProduct(product);
//			detail.setOrder(order);
//		}
//
//		Order createdOrder = orderRepository.save(order);
//		return ResponseEntity.ok(createdOrder);

//	}

}
