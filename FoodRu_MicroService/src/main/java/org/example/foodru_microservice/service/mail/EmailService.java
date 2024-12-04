package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;

public interface EmailService {


    void sendEmailWithAttachment(String toAddress, byte[] bytes) throws MessagingException;
}