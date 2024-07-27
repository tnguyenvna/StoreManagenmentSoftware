package com.example.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderDetailRepository;
import com.example.repository.OrderRepository;
import com.example.repository.PaymentRepository;
import com.example.util.MessageUtil;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	public ResponseEntity<Customer> getCustomerById(int id) {
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	public ResponseEntity<Customer> createCustomer(Customer customer) {
		// Ensure created_at is set correctly
		if (customer.getCreated_at() == null) {
			customer.setCreated_at(new Timestamp(System.currentTimeMillis()));
		}
		Customer createdCustomer = customerRepository.save(customer);
		return ResponseEntity.ok(createdCustomer);
	}

	public ResponseEntity<Customer> updateCustomer(int id, Customer customerDetails) {
		// Ensure created_at is set correctly
		Customer customer = customerRepository.findById(id).orElse(null);

		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		customer.setName(customerDetails.getName());
		customer.setEmail(customerDetails.getEmail());
		customer.setAddress(customerDetails.getAddress());
		customer.setPhone(customerDetails.getPhone());

		Customer updateCustomer = customerRepository.save(customer);

		return ResponseEntity.ok(updateCustomer);
	}

	@Transactional
	public ResponseEntity<Map<String, String>> deleteCustomerById(int id) {
		// Xóa tất cả các chi tiết đơn hàng liên quan đến các đơn hàng của khách hàng
		List<Order> orders = orderRepository.findByCustomerId(id);
		for (Order order : orders) {
			orderDetailRepository.deleteOrderDetailByOrderId(order.getId());
		}

		// Xóa thanh toán
		paymentRepository.deletePaymentsByOrderId(id);
		;

		// Xóa tất cả các đơn hàng liên quan đến khách hàng
		orderRepository.deleteOrderByCustomerId(id);

		// Xóa khách hàng
		customerRepository.deleteById(id);

		// Tạo thông điệp xác nhận
		Map<String, String> response = new HashMap<>();
		response.put("message", "Customer deleted successfully.");

		// Trả về mã trạng thái 200 (OK) cùng với thông điệp
		return MessageUtil.customerDeletedSuccessfullyResponse();
	}
}
