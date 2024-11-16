package org.example.authentication_service.service.auth;

import jakarta.mail.MessagingException;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createAuthToken(LoginUserDto authRequest);

    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) ;

    ResponseEntity<?> confirmUserAccount(String confirmationToken);
}