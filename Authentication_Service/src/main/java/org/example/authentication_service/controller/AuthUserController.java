package org.example.authentication_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.model.dto.LoginUserDto;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthUserController {


    private final AuthService authService;


    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<?> createAuthToken(@Valid
                                             @RequestBody
                                             LoginUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping(EndPoints.REGISTRATION)
    public ResponseEntity<?> createNewUser(@Valid
                                           @RequestBody
                                           RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping(EndPoints.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid
                                           @RequestBody
                                           PasswordResetRequest request) {
        return authService.resetPassword(request);
    }


}
