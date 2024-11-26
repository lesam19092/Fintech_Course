/*
package org.example.authentication_service.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.authentication_service.model.consts.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testSendEmail() throws MessagingException {
        String toAddress = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        emailService.sendEmailWithVerification(toAddress, "testToken");

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(1)).send(mimeMessageCaptor.capture());

        MimeMessage capturedMimeMessage = mimeMessageCaptor.getValue();
        MimeMessageHelper helper = new MimeMessageHelper(capturedMimeMessage, true);

        helper.getMimeMessage().getSubject();

        assertEquals(toAddress, helper.getMimeMessage().getAllRecipients()[0].toString());
        assertEquals(Email.COMPLETE_REGISTRATION, helper.getMimeMessage().getSubject());
        assertEquals(Email.CONFIRM_MESSAGE + "testToken", helper.getMimeMessage().ge());
    }
}*/
