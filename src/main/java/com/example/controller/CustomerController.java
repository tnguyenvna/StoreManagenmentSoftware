package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.entity.Customer;
import com.example.service.implement.ICustomerSevice;
import com.example.util.MessageUtil;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
	private ICustomerSevice iCustomerSevice;

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = iCustomerSevice.getAllCustomers();
		if (customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCustomerById(@PathVariable int id) {
		Optional<Customer> customerOpt = iCustomerSevice.getCustomerById(id);
		if (customerOpt.isPresent()) {
			return ResponseEntity.ok(customerOpt.get());
		}
		return MessageUtil.customerNotFoundResponse();
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> createCustomer(@RequestBody Customer customer) {
		Customer createdCustomer = iCustomerSevice.createCustomer(customer);
		return MessageUtil.customerCreatedSuccessfullyResponse(createdCustomer.getId());

	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateCustomerById(@PathVariable int id,
			@RequestBody Customer customerDetails) {
		Customer updatedCustomer = iCustomerSevice.updateCustomerById(id, customerDetails);
		if (updatedCustomer == null) {
			return MessageUtil.customerNotFoundResponse();
		}
		return MessageUtil.customerUpdatedSuccessfullyResponse();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteCustomerById(@PathVariable int id) {
		boolean isDeleted = iCustomerSevice.deleteCustomerById(id);
		if(!isDeleted) {
			return MessageUtil.customerNotFoundResponse();
		}
		return MessageUtil.customerDeletedSuccessfullyResponse();
	}

}
