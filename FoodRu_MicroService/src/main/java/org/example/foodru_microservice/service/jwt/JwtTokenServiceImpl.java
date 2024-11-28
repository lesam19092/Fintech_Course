package org.example.foodru_microservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Data
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {


    private String secret;

    @Override
    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    @Override
    public String getUsername(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String instance = claims.get("instance", String.class);
        if (!"FoodRu".equals(instance)) {
            throw new IllegalArgumentException("Invalid instance in token");
        }
        return claims.get("username", String.class);
    }

    @Override
    public String getEmail(String token) {
        return getAllClaimsFromToken(token).get("email", String.class);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}