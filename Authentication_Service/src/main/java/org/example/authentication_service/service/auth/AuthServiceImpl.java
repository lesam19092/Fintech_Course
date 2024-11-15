package org.example.authentication_service.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.controller.dto.JwtResponse;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
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

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();


        check.checkVerification(authRequest);
        //todo тут костыль
        daoAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(username + ":" + authRequest.getUserType().name(), password));


        User user = userService.findUserByNameAndInstance(username, authRequest.getUserType().name());
        String token = jwtTokenService.generateToken(user,
                authRequest.getRememberMe() ? 1000
                        : 10);

        log.info("Auth token created successfully for user: {}", username);
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


    //todo реализовать другую логику
    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest request) {
        log.info("Resetting password for user: {}", request.getName());
        userService.resetPassword(request);
        log.info("Password reset successfully for user: {}", request.getName());
        return ResponseEntity.ok("Password reset successfully");
    }

    @Override
    public ResponseEntity<?> confirmUserAccount(String confirmationToken) {
        if (userService.isEmailConfirmed(confirmationToken)) {
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.ok("Email already verified!");
    }


}