package com.ceramicsheaven.controllers;

import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Cart;
import com.ceramicsheaven.model.CartItems;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.AddItemRequest;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.services.CartService;
import com.ceramicsheaven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/cart")
@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;

	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		List<CartItems> cartItemsList = new ArrayList<>(cart.getCartItems());
		Collections.sort(cartItemsList, Comparator.comparing(CartItems::getId));
		cart.setCartItems(new HashSet<>(cartItemsList));
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest request, @RequestHeader("Authorization") String jwt)throws UserException , ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		
		String res = cartService.addCartItem(user.getId(), request);
		
		ApiResponse response = new ApiResponse();
		response.setMessage(res);
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}


}
