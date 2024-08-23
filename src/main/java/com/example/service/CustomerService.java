package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.implement.ICustomerSevice;

import jakarta.transaction.Transactional;

@Service
public class CustomerService implements ICustomerSevice {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAllByIsActiveTrue();
	}

	public Optional<Customer> getCustomerById(int id) {
		return customerRepository.findByIdAndIsActiveTrue(id);

	}

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer updateCustomerById(int id, Customer customerDetails) {
		// Ensure created_at is set correctly
		Optional<Customer> customerOpt = customerRepository.findByIdAndIsActiveTrue(id);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(customerDetails.getName());
			customer.setEmail(customerDetails.getEmail());
			customer.setAddress(customerDetails.getAddress());
			customer.setPhone(customerDetails.getPhone());
			return customerRepository.save(customer);
		}
		return null;
	}

	@Transactional
	public boolean deleteCustomerById(int customerId) {
		Optional<Customer> customerOpt = customerRepository.findByIdAndIsActiveTrue(customerId);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setActive(false);
			customerRepository.save(customer);
			return true;
		}
		return false;
	}

}
