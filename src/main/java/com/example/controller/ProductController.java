package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.util.MessageUtil;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) {
		Optional<Product> productOpt = productService.getProductById(id);
		if (productOpt.isPresent()) {
			return ResponseEntity.ok(productOpt.get());
		}
		return MessageUtil.productNotFoundResponse();
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return MessageUtil.productCreatedSuccessfullyResponse(createdProduct.getId());
	}

	@PostMapping("/{id}/uploadImage")
	public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("image") MultipartFile file) {
		try {
			String imageUrl = productService.saveProductImage(id, file);
			return MessageUtil.imageUploadSuccessResponse(imageUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.imageUploadErrorResponse();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateProduct(@PathVariable int id,
			@RequestBody Product productDetails) {
		Product updatedProduct = productService.updateProduct(id, productDetails);
		if (updatedProduct == null) {
			return MessageUtil.productNotFoundResponse();
		}
		return MessageUtil.productUpdatedSuccessfullyResponse();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable int id) {
		boolean isDeleted = productService.deleteProduct(id);
		if (!isDeleted) {
			return MessageUtil.productNotFoundResponse();
		}
		return MessageUtil.productDeletedSuccessfullyResponse();
	}
}
