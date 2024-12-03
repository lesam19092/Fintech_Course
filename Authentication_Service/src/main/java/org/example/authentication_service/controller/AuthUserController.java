package org.example.authentication_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authentication_service.controller.api.AuthUserApi;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.model.consts.EndPoints;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPoints.AUTH)
@RequiredArgsConstructor
public class AuthUserController implements AuthUserApi {

    private final AuthService authService;


    @PostMapping(EndPoints.LOGIN)
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody LoginUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }


    @PostMapping(EndPoints.REGISTRATION)
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @GetMapping(EndPoints.CONFIRM_ACCOUNT)
    public ResponseEntity<?> confirmUserAccount(@RequestParam String confirmationToken) {
        return authService.confirmUserAccount(confirmationToken);
    }
}