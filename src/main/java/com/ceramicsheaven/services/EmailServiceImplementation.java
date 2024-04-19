package com.ceramicsheaven.services;

import com.ceramicsheaven.model.Address;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImplementation implements EmailService{
    private final JavaMailSender mailSender;

    public EmailServiceImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void registrationEmail(String email,String fullName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ceramicsheaven2023@gmail.com");
            message.setTo(email);
            message.setText("Hello,"+"\n"+fullName+"\n"+"congratulation for successful registration");
            message.setSubject("Registration Successful");
            mailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void orderPlaced(String fullName, String email, Long orderId,LocalDateTime orderDate, LocalDateTime deliveryDate, Double totalPrice, Integer discount, String paymentMethod,String paymentStatus, Address address) {
        try {
            totalPrice = totalPrice-50;
            Integer shippingCharges = 50;
            String orderDay = orderDate.getDayOfWeek().toString();
            String orderMonth = orderDate.getMonth().toString();
            String dateOrder = String.valueOf(orderDate.getDayOfMonth());

            orderDay = capitalizeFirstLetter(orderDay);
            orderMonth = capitalizeFirstLetter(orderMonth);

            String deliveryDay = deliveryDate.getDayOfWeek().toString();
            String deliveryMonth = deliveryDate.getMonth().toString();
            String dateDelivery = String.valueOf(deliveryDate.getDayOfMonth());

            deliveryDay = capitalizeFirstLetter(deliveryDay);
            deliveryMonth = capitalizeFirstLetter(deliveryMonth);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ceramicsheaven2023@gmail.com");
            message.setTo(email);
            message.setText("Hello, "+fullName+"\n"+"Thank you for your order. Your estimated delivery date is integrated below. If you would like to view the status of your order or make any changes to it, please visit your orders on CeramicsHeaven."+"\n\n"+
                    "______________________"+"\n\n"+
                    "Your order will be sent to:\n"+fullName+"\n"+address.toString()+"\n"+
                    "______________________"+"\n\n"+
                    "Order summary\n\n"+"Order #"+orderId+"\nPlaced on: "+orderDay+", "+dateOrder+orderMonth+"\nDelivered on: "+deliveryDay+", "+dateDelivery+deliveryMonth+"\n\n"+
                    "Total price"+"         "+totalPrice+"\n"+
                    "Shipping Charges"+"         "+shippingCharges+"\n"+
                    "You saved"+"           "+discount+"\n"+
                    "Payment status"+"      "+paymentStatus+"\n"+
                    "Payment method"+"      "+paymentMethod);
            message.setSubject("Order placed successfully");
            mailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }


}
