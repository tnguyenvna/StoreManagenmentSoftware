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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;
import com.example.service.implement.IProductService;
import com.example.util.MessageUtil;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private IProductService iProductService;

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = iProductService.getAllProducts();
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id) {
		Optional<Product> productOpt = iProductService.getProductById(id);
		if (productOpt.isPresent()) {
			return ResponseEntity.ok(productOpt.get());
		}
		return MessageUtil.productNotFoundResponse();
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> createProduct(@RequestBody Product product) {
		Product createdProduct = iProductService.createProduct(product);
		return MessageUtil.productCreatedSuccessfullyResponse(createdProduct.getId());
	}

	@PostMapping("/{id}/uploadImage")
	public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("image") MultipartFile file) {
		try {
			String imageUrl = iProductService.saveProductImage(id, file);
			return MessageUtil.imageUploadSuccessResponse(imageUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageUtil.imageUploadErrorResponse();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> updateProductById(@PathVariable int id,
			@RequestBody Product productDetails) {
		Product updatedProduct = iProductService.updateProductById(id, productDetails);
		if (updatedProduct == null) {
			return MessageUtil.productNotFoundResponse();
		}
		return MessageUtil.productUpdatedSuccessfullyResponse();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteProductById(@PathVariable int id) {
		boolean isDeleted = iProductService.deleteProductById(id);
		if (!isDeleted) {
			return MessageUtil.productNotFoundResponse();
		}
		return MessageUtil.productDeletedSuccessfullyResponse();
	}
}
