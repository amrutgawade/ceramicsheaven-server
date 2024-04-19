package com.ceramicsheaven.services;

import com.ceramicsheaven.model.Address;

import java.time.LocalDateTime;

public interface EmailService {
    public  void registrationEmail(String email,String fullName );

    public void orderPlaced(String fullName, String email, Long orderId,LocalDateTime orderDate, LocalDateTime deliveryDate, Double totalPrice, Integer discount, String paymentMethod,String paymentStatus, Address address);

    public String capitalizeFirstLetter(String str);
}
