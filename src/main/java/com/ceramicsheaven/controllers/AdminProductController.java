package com.ceramicsheaven.controllers;

import com.ceramicsheaven.model.Product;
import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.requests.ProductUpdateRequest;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.services.ProductService;
import com.ceramicsheaven.requests.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {



	private ProductService productService;

	@Autowired
	public AdminProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	
	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequest req) throws ProductException {
		
		Product product = productService.createProduct(req);
		
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
		
		productService.deleteProduct(productId);
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Deleted Successfully");
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}


	@PutMapping("{productId}/product")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId,@RequestBody ProductUpdateRequest req) throws ProductException{
		Product product = productService.updateProduct(productId, req);
		return new ResponseEntity<Product>(product,HttpStatus.CREATED);
		
	}

	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody ProductRequest[] req) throws ProductException {
		for(ProductRequest product : req) {
			productService.createProduct(product);
		}
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Product Create");
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
	}
}
