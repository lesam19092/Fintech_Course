package org.example.foodru_microservice.service.jwt;

import org.example.foodru_microservice.handler.exception.InvalidInstanceException;
import org.example.foodru_microservice.model.consts.JwtParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {

    @InjectMocks
    private JwtTokenServiceImpl jwtTokenService;

    private String token;

    @BeforeEach
    void setUp() {
        jwtTokenService.setSecret("mySecretKey");

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtParam.ROLE, "ROLE_USER");
        claims.put(JwtParam.INSTANCE, "FoodRu");
        claims.put(JwtParam.USERNAME, "testUser");
        claims.put(JwtParam.EMAIL, "test@example.com");

        token = Jwts.builder()
                .setClaims(claims)
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtTokenService.getSecret())
                .compact();
    }

    @Test
    void getRole() {
        String role = jwtTokenService.getRole(token);
        assertEquals("ROLE_USER", role);
    }

    @Test
    void getUsername() {
        String username = jwtTokenService.getUsername(token);
        assertEquals("testUser", username);
    }

    @Test
    void getEmail() {
        String email = jwtTokenService.getEmail(token);
        assertEquals("test@example.com", email);
    }

    @Test
    void getUsername_InvalidInstance() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtParam.ROLE, "ROLE_USER");
        claims.put(JwtParam.INSTANCE, "InvalidInstance");
        claims.put(JwtParam.USERNAME, "testUser");
        claims.put(JwtParam.EMAIL, "test@example.com");

        String invalidToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtTokenService.getSecret())
                .compact();

        assertThrows(InvalidInstanceException.class, () -> jwtTokenService.getUsername(invalidToken));
    }
}