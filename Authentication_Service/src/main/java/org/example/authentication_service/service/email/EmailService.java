package org.example.authentication_service.service.email;


import jakarta.mail.MessagingException;

public interface EmailService {


    void sendEmailWithVerification(String toAddress, String confirmToken) throws MessagingException;

    void sendEmailWithRestorePassword(String toAddress, String passwordToken) throws MessagingException;
}