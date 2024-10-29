package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

@Service
@Data
@ConfigurationProperties("spring.mail")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private String username;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

  /*  @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }*/


    @Override
    public void sendEmailWithAttachment(String toAddress, byte[] bytes) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(username);
        helper.setTo(toAddress);
        helper.setSubject("вы заказали продукты");
        helper.setText("ваш чек");

        helper.addAttachment("чек.pdf", new ByteArrayDataSource(bytes, "application/pdf"));

        mailSender.send(mimeMessage);
    }
}