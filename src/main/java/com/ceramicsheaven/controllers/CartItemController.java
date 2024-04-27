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

    private CartItemService cartItemService;
    private UserService userService;

    @Autowired
    public CartItemController(CartItemService cartItemService, UserService userService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getId(), id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Cart Item Removed Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
    @PutMapping("/increment/{cartItemId}")
    public ResponseEntity<ApiResponse> incrementQuantity(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartItemException {
        userService.findUserProfileByJwt(jwt);

        String res = cartItemService.incrementQuantity(cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage(res);
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @PutMapping("/decrement/{cartItemId}")
    public ResponseEntity<ApiResponse> decrementQuantity(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws UserException, ProductException, CartItemException {
        userService.findUserProfileByJwt(jwt);

        String res = cartItemService.decrementQuantity(cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage(res);
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

}
