package com.example.service.implement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;

public interface IProductService {

	List<Product> getAllProducts();

	Optional<Product> getProductById(int id);

	Product createProduct(Product product);

	Product updateProduct(int id, Product productDetail);

	boolean deleteProduct(int productId);

	String saveProductImage(int id, MultipartFile file) throws IOException;
}
