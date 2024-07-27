package com.example.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.entity.Product;

public class MessageUtil {

	public static Map<String, String> response = new HashMap<>();

	public static ResponseEntity<Map<String, String>> createResponseEntity(String message, HttpStatus status) {
		response.put("message", message);
		return new ResponseEntity<>(response, status);
	}

	// product
	public static ResponseEntity<Map<String, String>> productNotFoundResponse() {
		return createResponseEntity("Product not found.", HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<Map<String, String>> productCreatedSuccessfullyResponse(int productId) {
		return createResponseEntity("Product created successfully with ID: " + productId, HttpStatus.CREATED);
	}

	public static ResponseEntity<Map<String, String>> productUpdatedSuccessfullyResponse() {
		return createResponseEntity("Product updated successfully.", HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, String>> productDeletedSuccessfullyResponse() {
		return createResponseEntity("Product deleted successfully.", HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, String>> imageUploadSuccessResponse(String imageUrl) {
		response.put("message", "Image uploaded successfully");
		response.put("imageUrl", imageUrl);
		return ResponseEntity.ok(response);
	}

	public static ResponseEntity<Map<String, String>> imageUploadErrorResponse() {
		return createResponseEntity("Image upload failed.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//
	public static ResponseEntity<Map<String, String>> orderNotFoundResponse() {
		return createResponseEntity("Order not found.", HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<Map<String, String>> orderUpdateSuccessfullyResponse() {
		return createResponseEntity("Order updated successfully.", HttpStatus.OK);
	}

	public static ResponseEntity<Map<String, String>> customerDeletedSuccessfullyResponse() {
		return createResponseEntity("Customer deleted successfully.", HttpStatus.OK);
	}
}
