package org.example.authentication_service.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.controller.dto.JwtResponse;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.model.consts.Duration;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.check.CheckService;
import org.example.authentication_service.service.jwt.JwtTokenService;
import org.example.authentication_service.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final DaoAuthenticationProvider daoAuthProvider;
    private final CheckService check;


    @Override
    public ResponseEntity<?> createAuthToken(LoginUserDto authRequest) {
        log.info("Creating auth token for user: {}", authRequest.getUsername());
        authenticateUser(authRequest);
        User user = userService.findUserByNameAndInstance(authRequest.getUsername(), authRequest.getUserType().name());
        String token = jwtTokenService.generateToken(user,
                authRequest.getRememberMe() ? Duration.LONG
                        : Duration.SHORT);
        log.info("Auth token created successfully for user: {}", authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        log.info("Creating new user: {}", registrationUserDto.getUsername());
        check.checkUser(registrationUserDto);
        userService.createNewUser(registrationUserDto);
        log.info("New user created successfully: {}", registrationUserDto.getUsername());
        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public ResponseEntity<String> confirmUserAccount(String confirmationToken) {
        boolean isConfirmed = userService.isEmailConfirmed(confirmationToken);
        String message = isConfirmed ? "Email verified successfully!" : "Email already verified!";
        return ResponseEntity.ok(message);
    }

    private void authenticateUser(LoginUserDto authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        String userType = authRequest.getUserType().name();
        check.checkVerification(authRequest);
        daoAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(username + ":" + userType, password));
    }

}