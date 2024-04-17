package com.ceramicsheaven.controllers;

import com.ceramicsheaven.model.User;
import com.ceramicsheaven.exceptions.CartItemException;
import com.ceramicsheaven.exceptions.ProductException;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.requests.AddItemRequest;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.services.CartItemService;
import com.ceramicsheaven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getId(), id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Cart Item Removed Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}/{quantity}")
    public ResponseEntity<ApiResponse> UpdateItemToCart(@PathVariable Long cartItemId,@PathVariable Integer quantity, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartItemException {
        userService.findUserProfileByJwt(jwt);

        String res = cartItemService.updateCartItems(cartItemId,quantity);

        ApiResponse response = new ApiResponse();
        response.setMessage(res);
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

}
