package com.ceramicsheaven.services;

import com.ceramicsheaven.entities.Customer;
import com.ceramicsheaven.entities.LoginDTO;

public interface CustomerService {
    public Customer register(Customer customer);
    public Customer login (LoginDTO loginDTO);
}
