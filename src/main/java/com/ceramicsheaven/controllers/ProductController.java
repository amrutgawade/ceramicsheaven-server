package com.ceramicsheaven.controllers;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.Product;
import com.ceramicsheaven.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategory(@RequestParam String category,
															   @RequestParam List<String> color,
															   @RequestParam List<String> size,
															   @RequestParam(required = false) Integer minPrice,
															   @RequestParam(required = false) Integer maxPrice,
															   @RequestParam(required = false) Integer minDiscount,
															   @RequestParam String sort,
															   @RequestParam String stock,
															   @RequestParam Integer pagenumber,
															   @RequestParam Integer pageSize) throws ProductException {

		Page<Product> productPage = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pagenumber, pageSize);

		return new ResponseEntity<>(productPage, HttpStatus.ACCEPTED);
	}





	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId)throws ProductException{
		
		Product product = productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}
}
