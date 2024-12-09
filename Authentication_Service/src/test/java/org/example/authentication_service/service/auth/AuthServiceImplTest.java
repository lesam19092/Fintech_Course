package org.example.authentication_service.service.auth;

import org.example.authentication_service.controller.dto.JwtResponse;
import org.example.authentication_service.controller.dto.LoginUserDto;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.model.consts.Duration;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.check.CheckService;
import org.example.authentication_service.service.jwt.JwtTokenService;
import org.example.authentication_service.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {


    @Mock
    private UserService userService;
    @Mock
    private JwtTokenService jwtTokenService;
    @Mock
    private DaoAuthenticationProvider daoAuthProvider;
    @Mock
    private CheckService check;
    @InjectMocks
    private AuthServiceImpl authService;




    @Test
    void createAuthToken() {
        LoginUserDto authRequest = new LoginUserDto();
        authRequest.setUsername("testUser");
        authRequest.setPassword("testPassword");
        authRequest.setUserType(UserType.Edadil);
        authRequest.setRememberMe(true);


        User user = new User();
        user.setName("testUser");

        when(userService.findUserByNameAndInstance(anyString(), anyString())).thenReturn(user);
        when(jwtTokenService.generateToken(user, Duration.LONG)).thenReturn("testToken");

        ResponseEntity<?> response = authService.createAuthToken(authRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof JwtResponse);
        assertEquals("testToken", ((JwtResponse) response.getBody()).getToken());

    }

    @Test
    void createNewUser() {
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("newUser");
        registrationUserDto.setPassword("newPassword");

        doNothing().when(check).checkUser(any(RegistrationUserDto.class));
        doNothing().when(userService).createNewUser(any(RegistrationUserDto.class));

        ResponseEntity<?> response = authService.createNewUser(registrationUserDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Verify email by the link sent on your email address", response.getBody());

    }

    @Test
    void confirmUserAccount() {
        String confirmationToken = "testToken";

        when(userService.isEmailConfirmed(anyString())).thenReturn(true);

        ResponseEntity<String> response = authService.confirmUserAccount(confirmationToken);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Email verified successfully!", response.getBody());

    }
}