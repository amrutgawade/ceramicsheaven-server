package com.ceramicsheaven.controllers;

import com.ceramicsheaven.config.JwtProvider;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.repositories.UserRepository;
import com.ceramicsheaven.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminUserController {

    private UserRepository userRepository;

    @Autowired
    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @PostMapping("/allUsers")
    public  ResponseEntity<List<User>> getUsers(){
       List<User> users = userRepository.findAll();
       return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
   }

   @DeleteMapping("/deleteUser/{userId}")
    public  ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
        ApiResponse response = new ApiResponse();
        response.setMessage("User Deleted Successfully");
        response.setStatus(true);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
   }
}
