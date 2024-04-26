package com.ceramicsheaven.controllers;

import com.ceramicsheaven.config.JwtProvider;
import com.ceramicsheaven.model.Order;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.repositories.UserRepository;
import com.ceramicsheaven.responses.AdminOrdersAndUsers;
import com.ceramicsheaven.responses.ApiResponse;
import com.ceramicsheaven.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminUserController {

    private UserRepository userRepository;
    private OrderService orderService;

    @Autowired
    public AdminUserController(UserRepository userRepository, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderService = orderService;

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

    @GetMapping("/totalData")
    public ResponseEntity<AdminOrdersAndUsers> getTotalSales(){
        AdminOrdersAndUsers data = orderService.getTotalData();
        return  new ResponseEntity<AdminOrdersAndUsers>(data,HttpStatus.OK);
    }
}
