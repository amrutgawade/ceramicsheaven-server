package com.ceramicsheaven.repositories;

import com.ceramicsheaven.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    public List<Address> findAllByUserId(Long userId);
}
