package org.example.authentication_service.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.model.dto.*;
import org.example.authentication_service.model.entity.UserFoodRu;
import org.example.authentication_service.service.jwt.JwtTokenService;
import org.example.authentication_service.service.user_foodru.UserFoodRuService;
import org.example.authentication_service.utils.Checker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFoodRuServiceImpl implements AuthService {

    private final UserFoodRuService userFoodRuService;
    private final JwtTokenService jwtTokenService;
    @Qualifier("foodRuAuthProvider")
    private final DaoAuthenticationProvider foodRuAuthProvider;
    private final Checker checker;


    @Override
    public ResponseEntity<?> createAuthToken(LoginUserDto authRequest) {
        log.info("Creating auth token for user: {}", authRequest.getUsername());

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        foodRuAuthProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserDetails userDetails = userFoodRuService.loadUserByUsername(username);
        String token = jwtTokenService.generateToken(userDetails, authRequest.getRememberMe() ? 1000 : 10);
        log.info("Auth token created successfully for user: {}", username);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        log.info("Creating new user: {}", registrationUserDto.getUsername());

        checker.checkPassword(registrationUserDto);
        checker.checkUsername(registrationUserDto, UserType.FoodRu);

        UserFoodRu user = userFoodRuService.createNewUser(registrationUserDto);
        log.info("New user created successfully: {}", user.getUsername());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Override
    public ResponseEntity<?> resetPassword(PasswordResetRequest request) {
        log.info("Resetting password for user: {}", request.getName());
        userFoodRuService.resetPassword(request);
        log.info("Password reset successfully for user: {}", request.getName());
        return ResponseEntity.ok("Password reset successfully");
    }

}