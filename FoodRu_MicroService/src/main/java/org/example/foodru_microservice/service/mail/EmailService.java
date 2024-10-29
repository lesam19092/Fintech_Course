package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface EmailService {


    void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException;
}