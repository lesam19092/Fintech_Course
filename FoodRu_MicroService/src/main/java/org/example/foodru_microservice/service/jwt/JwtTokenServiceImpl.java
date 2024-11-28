package org.example.foodru_microservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {


    private String secret;

    @Override
    public String getUsername(String token) {
        String instance = getAllClaimsFromToken(token).get("instance", String.class);
        if (!"FoodRu".equals(instance)) {
            throw new IllegalArgumentException("Invalid instance in token");
        }
        return instance;
    }

    public List<String> getRoles(String token) {
        String roles = getAllClaimsFromToken(token).get("role", String.class);
        return List.of(roles);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}