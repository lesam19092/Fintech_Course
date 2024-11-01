package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(username);
        helper.setTo(toAddress);
        helper.setSubject("вы заказали продукты");
        helper.setText("ваш чек");
        //todo спросить нужно ли это выносить в отдельные проперти
        helper.addAttachment("чек.pdf", new ByteArrayDataSource(bytes, "application/pdf"));

        log.info("Sending email to {}", toAddress);
        mailSender.send(mimeMessage);
        log.info("Email sent successfully to {}", toAddress);
    }
}