package com.ceramicsheaven.controllers;

import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Address;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.requests.UpdatePasswordRequest;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;


	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt)throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}

	@PutMapping("/profile/update")
	public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt,@RequestBody User user)throws UserException {
		User updatedProfile = userService.updateUser(jwt,user);
		return new ResponseEntity<User>(updatedProfile,HttpStatus.ACCEPTED);
	}

	@PostMapping("/profile/updatePassword")
	public ResponseEntity<ApiResponse> updatePassword(@RequestHeader("Authorization") String jwt,@RequestBody UpdatePasswordRequest updatePasswordRequest) throws UserException {
		String response =  userService.updatePassword(jwt,updatePasswordRequest);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(response);
		apiResponse.setStatus(true);
		return  new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
	}

	@PostMapping("/profile/address")
	public ResponseEntity<ApiResponse> addAddress(@RequestHeader("Authorization") String jwt,@RequestBody Address address) throws UserException {
		String response = userService.addAddress(jwt,address);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(response);
		apiResponse.setStatus(true);
		return  new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
	}

}
