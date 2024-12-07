package org.example.foodru_microservice.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.foodru_microservice.model.consts.EmailConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void sendEmailWithAttachment() throws MessagingException {
        String toAddress = "test@example.com";
        byte[] attachmentBytes = new byte[]{1, 2, 3};

        emailService.setUsername("test@sender.com");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendEmailWithAttachment(toAddress, attachmentBytes);

        verify(mailSender).send(mimeMessage);

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(mimeMessageCaptor.capture());

        MimeMessage capturedMimeMessage = mimeMessageCaptor.getValue();
        when(mimeMessageCaptor.getValue().getSubject()).thenReturn("вы заказали продукты");

        assertEquals(EmailConstants.SUBJECT, capturedMimeMessage.getSubject());
    }
}
