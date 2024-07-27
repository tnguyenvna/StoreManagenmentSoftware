package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Payment;
import com.example.repository.PaymentRepository;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentRepository paymentRepository;

	@GetMapping
	public List<Payment> getAllPayments() {
		return paymentRepository.findAll();
	}

	@PostMapping
	public Payment createPayment(@RequestBody Payment payment) {
		return paymentRepository.save(payment);
	}

	@GetMapping("/{id}")
	public Payment getPaymentById(@PathVariable int id) {
		return paymentRepository.findById(id).orElse(null);
	}

	@PutMapping("/{id}")
	public Payment updatePayment(@PathVariable int id, @RequestBody Payment paymentDetails) {
		Payment payment = paymentRepository.findById(id).orElse(null);
		if (payment != null) {
			payment.setMethod(paymentDetails.getMethod());
			payment.setAmount(paymentDetails.getAmount());
			return paymentRepository.save(payment);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	public void deletePayment(@PathVariable int id) {
		paymentRepository.deleteById(id);
	}
}
