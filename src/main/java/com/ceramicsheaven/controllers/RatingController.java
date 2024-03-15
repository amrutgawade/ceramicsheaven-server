package com.ceramicsheaven.controllers;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Rating;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.RatingRequest;
import com.ceramicsheaven.services.RatingService;
import com.ceramicsheaven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest, @RequestHeader("Authorization") String jwt)throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		System.out.println(ratingRequest);
		Rating rating = ratingService.createRating(ratingRequest, user);
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long productId,@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Rating> rating = ratingService.getProductRating(productId);
		
		return new ResponseEntity<List<Rating>>(rating,HttpStatus.CREATED);
	}
}
