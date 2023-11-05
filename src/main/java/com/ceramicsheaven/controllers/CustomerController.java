package com.ceramicsheaven.controllers;

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
}
