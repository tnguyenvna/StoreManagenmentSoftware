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

import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
		return customerService.getCustomerById(id);

	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		return customerService.createCustomer(customer);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails) {
		return customerService.updateCustomer(id, customerDetails);
	}

	@DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomerById(@PathVariable int id) {
        return customerService.deleteCustomerById(id);
    }

	@GetMapping("{id}/orders")
	public List<Order> getOrdersByCustomerId(@PathVariable int id) {
		return customerRepository.findById(id).map(Customer::getOrders).orElse(null);
	}

}
