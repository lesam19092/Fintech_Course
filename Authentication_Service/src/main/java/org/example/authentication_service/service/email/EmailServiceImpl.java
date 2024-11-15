package org.example.authentication_service.service.email;


import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.consts.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    //todo добавить properties

    @Override
    @SneakyThrows
    @Async("forSendingEmail")
    public void sendEmail(String toAddress, String confirmToken) {
        log.info("Creating MIME message for email to {}", toAddress);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);


        helper.setFrom(username);
        helper.setTo(toAddress);
        helper.setSubject(Email.SUBJECT);
        helper.setText(Email.MESSAGE + confirmToken);


        log.info("Sending email to {}", toAddress);
        mailSender.send(mimeMessage);
        log.info("Email sent successfully to {}", toAddress);

    }
}