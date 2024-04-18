package com.ceramicsheaven.services;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.*;
import com.ceramicsheaven.repositories.CartItemRepository;
import com.ceramicsheaven.exceptions.CartItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService{

	private CartItemRepository cartItemRepository;
	private UserService userService;
	
	
	@Autowired

	public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.userService = userService;
	}

	@Override
	public CartItems creaCartItems(CartItems cartItems) {
//		cartItems.setQuantity(1);
//		cartItems.setPrice(cartItems.getProduct().getPrice()*cartItems.getQuantity());
//		cartItems.setDiscountedPrice(cartItems.getProduct().getDiscountedPrice()*cartItems.getQuantity());
		
		CartItems createdCartItem = cartItemRepository.save(cartItems);
		
		return createdCartItem;
	}

//	@Override
//	public String updateCartItems(Long cartItemId, Integer quantity)throws CartItemException {
//		CartItems item = findCartItemById(cartItemId);
//		User user = userService.findById(item.getId());
//		item.setQuantity(quantity);
//		item.setPrice(quantity*item.getProduct().getPrice());
//		item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
//		cartItemRepository.save(item);
//		return "updated Successfully";
//	}

	@Override
	public String incrementQuantity(Long cartItemId) throws CartItemException, ProductException {
		String res = null;
		CartItems item = findCartItemById(cartItemId);
		Integer quantity = item.getQuantity();
		String[] dimensions = item.getSize().split("X");
			if (dimensions.length != 2) {
				throw new ProductException("Invalid size format");
			}
			int width = Integer.parseInt(dimensions[0].trim());
			int height = Integer.parseInt(dimensions[1].trim());

			Product product = item.getProduct();
			for (Size size : product.getSizes()) {
				if (size.getWidth() == width && size.getHeight() == height) {
					if (quantity.equals(size.getQuantity())){
						 res =  "Quantity reach out";
					}else{
						quantity +=1;
						item.setQuantity(quantity);
						item.setPrice(quantity*item.getProduct().getPrice());
						item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
						cartItemRepository.save(item);
						 res =  "Quantity Updated";
					}
				}
			}
		return res;
	}

	@Override
	public String decrementQuantity(Long cartItemId) throws CartItemException,ProductException{
		String res = null;
		CartItems item = findCartItemById(cartItemId);
		Integer quantity = item.getQuantity();
		String[] dimensions = item.getSize().split("X");
		if (dimensions.length != 2) {
			throw new ProductException("Invalid size format");
		}
		int width = Integer.parseInt(dimensions[0].trim());
		int height = Integer.parseInt(dimensions[1].trim());

		Product product = item.getProduct();
		for (Size size : product.getSizes()) {
			if (size.getWidth() == width && size.getHeight() == height) {
				if (quantity==1){
					res =  "At-least 1 Quantity";
				}else{
					quantity -=1;
					item.setQuantity(quantity);
					item.setPrice(quantity*item.getProduct().getPrice());
					item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
					cartItemRepository.save(item);
					res =  "Quantity Updated";
				}
			}
		}
		return res;
	}


	@Override
	public CartItems isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItems cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItems cartItem = findCartItemById(cartItemId);
		
		User user = userService.findById(cartItem.getUserId());
		
		User reqUser = userService.findById(userId);
		
		if (user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}else {
			throw new UserException("You Cant Remove another users item");
		}
	}

	@Override
	public CartItems findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItems> optional = cartItemRepository.findById(cartItemId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		
		throw new CartItemException("Cart Item Not Found with id : "+cartItemId);
	}

}
