package org.example.authentication_service.controller;

/*

import jakarta.validation.Valid;
import org.example.authentication_service.model.dto.LoginUserDto;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edadil")
public class EdadilUserController {

    private final AuthService authService;

    public EdadilUserController(
            @Qualifier("authEdadilServiceImpl")
            AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody LoginUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid
                                           @RequestBody
                                           RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid
                                           @RequestBody
                                           PasswordResetRequest request) {
        return authService.resetPassword(request);
    }


}*/
