package org.example.authentication_service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.model.dto.LoginUserDto;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationStrategy strategyFactory;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto authRequest, @RequestParam UserType userType) {
        AuthService strategy = strategyFactory.getStrategy(userType);
        return strategy.createAuthToken(authRequest);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request, @RequestParam UserType userType) {
        AuthService strategy = strategyFactory.getStrategy(userType);
        return strategy.resetPassword(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDto registrationUserDto, @RequestParam UserType userType) {
        AuthService strategy = strategyFactory.getStrategy(userType);
        return strategy.createNewUser(registrationUserDto);
    }
}