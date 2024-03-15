package com.ceramicsheaven.services;


import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.Review;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.ReviewRequest;

import java.util.List;

public interface ReviewService {

	public Review createReview(ReviewRequest req, User user)throws ProductException;
	
	public List<Review> getAllReview(Long productId);
}
