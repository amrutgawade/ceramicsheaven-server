package com.ceramicsheaven.services;

import com.ceramicsheaven.entities.Customer;
import com.ceramicsheaven.entities.LoginDTO;
import com.ceramicsheaven.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements com.ceramicsheaven.services.CustomerService {

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerServiceImp(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public Customer register(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer login(LoginDTO loginDTO) {
        Customer customer = customerRepository.findOneByEmailAndPassword(loginDTO.getEmail(),loginDTO.getPassword());
        return customer;
    }
}
