package com.ceramicsheaven.controllers;

import com.ceramicsheaven.entities.LoginDTO;
import com.ceramicsheaven.services.CustomerService;
import com.ceramicsheaven.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CustomerController {

    CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService=customerService;
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer){
        return customerService.register(customer);
    }

    @PostMapping("/login")
    public Customer login(@RequestBody LoginDTO loginDTO){
        return customerService.login(loginDTO);
    }
}
