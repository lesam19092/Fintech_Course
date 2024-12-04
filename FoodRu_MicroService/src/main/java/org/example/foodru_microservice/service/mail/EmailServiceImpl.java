package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.model.consts.EmailConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendEmailWithAttachment(String toAddress, byte[] bytes) throws MessagingException {
        log.info("Creating MIME message for email to {}", toAddress);
        MimeMessage mimeMessage = createMimeMessage(toAddress, bytes);
        mailSender.send(mimeMessage);
        log.info("Email sent successfully to {}", toAddress);
    }

    private MimeMessage createMimeMessage(String toAddress, byte[] bytes) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(username);
        helper.setTo(toAddress);
        helper.setSubject(EmailConstants.SUBJECT);
        helper.setText(EmailConstants.TEXT);
        helper.addAttachment(EmailConstants.ATTACHMENT_NAME, new ByteArrayDataSource(bytes, EmailConstants.ATTACHMENT_TYPE));

        return mimeMessage;
    }
}