package org.example.authentication_service.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.dto.*;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.jwt.JwtTokenService;
import org.example.authentication_service.service.user.UserService;
import org.example.authentication_service.utils.Checker;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    //todo убрать
    private final UserService userServicea;
    private final JwtTokenService jwtTokenService;
    private final DaoAuthenticationProvider daoAuthProvider;
    private final Checker checker;


    @Override
    public ResponseEntity<?> createAuthToken(LoginUserDto authRequest) {
        log.info("Creating auth token for user: {}", authRequest.getUsername());

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        daoAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserDetails userDetails = userServicea.loadUserByUsername(username);
        String token = jwtTokenService.generateToken(userDetails,
                authRequest.getRememberMe() ? 1000
                        : 10);

        log.info("Auth token created successfully for user: {}", username);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        log.info("Creating new user: {}", registrationUserDto.getUsername());

        checker.checkPassword(registrationUserDto);
        checker.checkUsername(registrationUserDto);

        User user = userServicea.createNewUser(registrationUserDto);
        log.info("New user created successfully: {}", user.getUsername());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest request) {
        log.info("Resetting password for user: {}", request.getName());
        userServicea.resetPassword(request);
        log.info("Password reset successfully for user: {}", request.getName());
        return ResponseEntity.ok("Password reset successfully");
    }


}