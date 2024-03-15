package com.ceramicsheaven.services;


import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.Rating;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.RatingRequest;

import java.util.List;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user)throws ProductException;
	
	public List<Rating> getProductRating(Long productId);
}
