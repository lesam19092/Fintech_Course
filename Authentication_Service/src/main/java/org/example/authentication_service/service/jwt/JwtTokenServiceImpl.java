package org.example.authentication_service.service.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.service.token.TokenService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {

    private String secret;

    private final TokenService tokenService;


    @Override
    public String generateToken(User user, Long minutes) {


        Map<String, Object> claims = createClaims(user);
        Date issuedDate = new Date();
        Date expiredDate = calculateExpirationDate(issuedDate, minutes);

        String jwtToken = buildJwtToken(claims, issuedDate, expiredDate);

        tokenService.revokeAllTokenByUser(user);
        tokenService.saveToken(user, jwtToken);

        return jwtToken;
    }


    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getName());
        claims.put("id", String.valueOf(user.getId()));
        claims.put("instance", user.getInstance().getName());
        claims.put("role", String.valueOf(user.getRole()));
        claims.put("email", user.getEmail());
        return claims;
    }

    private Date calculateExpirationDate(Date issuedDate, long minutes) {
        return new Date(issuedDate.getTime() + minutes * 60 * 1000);
    }

    private String buildJwtToken(Map<String, Object> claims, Date issuedDate, Date expiredDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}