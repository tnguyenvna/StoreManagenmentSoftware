package com.example.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.implement.IProductService;

@Service
public class ProductService implements IProductService{

	@Autowired
	private ProductRepository productRepository;

	private final String uploadDir = "uploads/";

	public List<Product> getAllProducts() {
		return productRepository.findAllByIsActiveTrue();
	}

	public Optional<Product> getProductById(int id) {
		return productRepository.findByIdAndIsActiveTrue(id);
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(int id, Product productDetail) {
		Optional<Product> productOpt = productRepository.findByIdAndIsActiveTrue(id);
		if (productOpt.isPresent()) {
			Product product = productOpt.get();
			product.setName(productDetail.getName());
			product.setDescription(productDetail.getDescription());
			product.setPrice(productDetail.getPrice());
			product.setStock(productDetail.getStock());
			product.setImageUrl(product.getImageUrl());
			return productRepository.save(product);
		}
		return null;
	}

	@Transactional
	public boolean deleteProduct(int productId) {
		Optional<Product> productOpt = productRepository.findByIdAndIsActiveTrue(productId);
		if (productOpt.isPresent()) {
			Product product = productOpt.get();
			product.setActive(false);
			productRepository.save(product);
			return true;
		}
		return false;
	}

	public String saveProductImage(int id, MultipartFile file) throws IOException {
		Optional<Product> productOpt = productRepository.findByIdAndIsActiveTrue(id);
		if (!productOpt.isPresent()) {
			throw new RuntimeException("product not found");
		}
		Product product = productOpt.get();

		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}

		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path filePath = Paths.get(uploadDir + fileName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		String imageUrl = "/uploads/" + fileName;
		product.setImageUrl(imageUrl);
		productRepository.save(product);

		return imageUrl;
	}
}
