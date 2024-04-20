package com.ceramicsheaven.services;

import com.ceramicsheaven.model.Address;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImplementation implements EmailService{
    private final JavaMailSender mailSender;

    private Configuration configuration;

    @Autowired
    public EmailServiceImplementation(JavaMailSender mailSender, Configuration configuration) {
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @Override
    public void registrationEmail(String email) {
        MimeMessage message = mailSender.createMimeMessage();
        Map<String,Object> model = new HashMap<>();
        try {
            MimeMessageHelper helper= new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Template template = configuration.getTemplate("emailRegistration.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            helper.setTo(email);
            helper.setText(html,true);
            helper.setSubject("Successful Registration");
            helper.setFrom("ceramicsheaven2023@gmail.com");
            mailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    @Override
    public void order(String fullName, String email, Long orderId,LocalDateTime orderDate, LocalDateTime deliveryDate, Integer totalPrice, Integer discount, String paymentMethod,String paymentStatus, Address address) {
        MimeMessage message = mailSender.createMimeMessage();
        Integer price = totalPrice-50;
        Integer shippingCharges = 50;
        String orderDay = orderDate.getDayOfWeek().toString();
        String orderMonth = orderDate.getMonth().toString();
        String dateOrder = String.valueOf(orderDate.getDayOfMonth());
        String orderYear = String.valueOf(orderDate.getYear());

                orderDay = capitalizeFirstLetter(orderDay);
        orderMonth = capitalizeFirstLetter(orderMonth);

        String deliveryDay = deliveryDate.getDayOfWeek().toString();
        String deliveryMonth = deliveryDate.getMonth().toString();
        String dateDelivery = String.valueOf(deliveryDate.getDayOfMonth());


        deliveryDay = capitalizeFirstLetter(deliveryDay);
        deliveryMonth = capitalizeFirstLetter(deliveryMonth);
        Map<String,Object> model = new HashMap<>();

        try {
            MimeMessageHelper helper= new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            fullName = "Hello "+fullName+",";
            String addressName = address.getFirstName()+" "+address.getLastName()+",";
            String order_Id = "Order Id: #"+orderId;
            String arrivingDate = deliveryDay+", "+deliveryMonth+" "+dateDelivery;
            String placedDate = "Placed on "+orderDay+", "+orderMonth+" "+dateOrder+", "+orderYear;
            String subTotal = "Rs."+price;
            String shipping = "Rs."+shippingCharges;
            totalPrice = totalPrice+50;
            String orderTotal = "Rs."+totalPrice;

            model.put("fullName",fullName);
            model.put("arrivingDate",arrivingDate);
            model.put("paymentMethod",paymentMethod);
            model.put("addressName",addressName);
            model.put("address",address.toString());
            model.put("orderId",order_Id);
            model.put("placedDate",placedDate);
            model.put("subTotal",subTotal);
            model.put("shipping",shipping);
            model.put("orderTotal",orderTotal);

            Template template = configuration.getTemplate("orderEmail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
            helper.setTo(email);
            helper.setText(html,true);
            helper.setSubject("Order placed successfully");
            helper.setFrom("ceramicsheaven2023@gmail.com");
            mailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }



}
