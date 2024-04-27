package com.ceramicsheaven.services;


import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Cart;
import com.ceramicsheaven.model.CartItems;
import com.ceramicsheaven.model.Product;
import com.ceramicsheaven.exceptions.CartItemException;

import java.util.List;

public interface CartItemService {

	public String incrementQuantity(Long cartItemId)throws CartItemException, ProductException;
	public String decrementQuantity(Long cartItemId)throws CartItemException,ProductException;
	
	public CartItems isCartItemExist(Cart cart, Product product, String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId)throws CartItemException, UserException;
	
	public CartItems findCartItemById(Long cartItemId) throws CartItemException;

	public void updateProductsQuantity(Long userId)throws ProductException;
}
