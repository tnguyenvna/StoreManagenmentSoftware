package com.example.service.implement;

import java.util.List;
import java.util.Optional;

import com.example.entity.Customer;

public interface ICustomerSevice {

	List<Customer> getAllCustomers();

	Optional<Customer> getCustomerById(int id);

	Customer createCustomer(Customer customer);

	Customer updateCustomerById(int id, Customer customerDetails);

	boolean deleteCustomerById(int id);
}
