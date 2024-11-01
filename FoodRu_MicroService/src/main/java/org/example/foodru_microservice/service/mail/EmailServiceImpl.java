package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

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