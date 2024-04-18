package com.ceramicsheaven.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
            message.setFrom("ceramicheaven2024@gmail.com");
            message.setTo(email);
            message.setText("Hello,"+"\n"+fullName);
            message.setSubject("Registration Successful");
            mailSender.send(message);

        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
