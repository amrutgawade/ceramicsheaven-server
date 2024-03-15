package com.ceramicsheaven.controllers;

import com.ceramicsheaven.model.Review;
import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.ReviewRequest;
import com.ceramicsheaven.services.ReviewService;
import com.ceramicsheaven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/create")
	public ResponseEntity<Review> CreateReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt)throws UserException, ProductException {
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Review review = reviewService.createReview(request, user);
		
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId) throws UserException,ProductException{
		
		List<Review> reviews = reviewService.getAllReview(productId);
		
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.ACCEPTED);
	}
	
}
