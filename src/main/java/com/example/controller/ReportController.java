package com.example.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping("/revenue")
	public BigDecimal getTotalRevennue() {
		return reportService.getTotalRevenue();
	}
	
	@GetMapping("/best-selling-product")
	public List<Product> getBestSellingProducts(){
		return reportService.getBestSellingproducts();
	}
}
