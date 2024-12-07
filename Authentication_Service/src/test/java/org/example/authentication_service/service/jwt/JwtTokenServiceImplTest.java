package org.example.authentication_service.service.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.model.entity.Role;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.token.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
class JwtTokenServiceImplTest {

    @InjectMocks
    private JwtTokenServiceImpl jwtTokenService;

    @Mock
    private TokenService tokenService;


    @BeforeEach
    void setUp() {
        jwtTokenService.setSecret("mySecretKey");
    }

    @Test
    void generateToken() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(Role.ROLE_USER);
        Instance instance = new Instance();
        instance.setName("TestInstance");
        user.setInstance(instance);

        String token = jwtTokenService.generateToken(user, 60L);


        assertNotNull(token);
    }

    @Test
    void tokenExpirationTimeIsCorrect() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(Role.ROLE_USER);
        Instance instance = new Instance();
        instance.setName("TestInstance");
        user.setInstance(instance);

        long tokenValidityMinutes = 60L;
        String token = jwtTokenService.generateToken(user, tokenValidityMinutes);

        Date issuedDate = new Date();
        Date expectedExpirationDate = new Date(issuedDate.getTime() + tokenValidityMinutes * 60 * 1000);

        Claims claims = Jwts.parser()
                .setSigningKey("mySecretKey")
                .parseClaimsJws(token)
                .getBody();

        Assertions.assertEquals(expectedExpirationDate.getTime(), claims.getExpiration().getTime(), 10);    }

    @Test
    void tokenExpirationTimeIsTenMinutes() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setRole(Role.ROLE_USER);
        Instance instance = new Instance();
        instance.setName("TestInstance");
        user.setInstance(instance);

        long tokenValidityMinutes = 10L;
        String token = jwtTokenService.generateToken(user, tokenValidityMinutes);

        Date issuedDate = new Date();
        Date expectedExpirationDate = new Date(issuedDate.getTime() + tokenValidityMinutes * 60 * 1000);

        Claims claims = Jwts.parser()
                .setSigningKey("mySecretKey")
                .parseClaimsJws(token)
                .getBody();

        Assertions.assertEquals(expectedExpirationDate.getTime(), claims.getExpiration().getTime(), 1000);
    }

}