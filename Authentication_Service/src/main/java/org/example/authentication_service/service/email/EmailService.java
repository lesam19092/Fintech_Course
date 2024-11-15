package org.example.authentication_service.service.email;

import jakarta.mail.MessagingException;

public interface EmailService {


    void sendEmail(String toAddress,String confirmToken) throws MessagingException;
}