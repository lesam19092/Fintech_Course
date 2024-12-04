package org.example.authentication_service.service.password;

import jakarta.mail.MessagingException;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.UpdatePasswordDto;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordService {

    ResponseEntity<?> resetPassword(PasswordResetRequest request) throws MessagingException;

    ResponseEntity<?> updatePassword(UpdatePasswordDto updatePasswordDto, String confirmationToken) throws MessagingException;
}