package com.ceramicsheaven.repositories;

import com.ceramicsheaven.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
