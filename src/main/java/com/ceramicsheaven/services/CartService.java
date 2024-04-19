package com.ceramicsheaven.services;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.model.Cart;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.AddItemRequest;

public interface CartService {

public Cart CreateCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);


}
