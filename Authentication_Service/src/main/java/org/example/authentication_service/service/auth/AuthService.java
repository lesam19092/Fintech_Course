package org.example.authentication_service.service.auth;

import org.example.authentication_service.model.dto.LoginUserDto;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> createAuthToken(LoginUserDto authRequest);

    ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto);

    ResponseEntity<?> resetPassword(PasswordResetRequest request);
}