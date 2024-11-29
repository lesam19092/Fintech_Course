package org.example.foodru_microservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.handler.exception.InvalidInstanceException;
import org.example.foodru_microservice.model.consts.JwtParam;
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
        return getAllClaimsFromToken(token).get(JwtParam.ROLE, String.class);
    }

    @Override
    public String getUsername(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String instance = claims.get(JwtParam.INSTANCE, String.class);
        if (!instance.equals("FoodRu")) {
            throw new InvalidInstanceException("Invalid instance in token");
        }
        return claims.get(JwtParam.USERNAME, String.class);
    }

    @Override
    public String getEmail(String token) {
        return getAllClaimsFromToken(token).get(JwtParam.EMAIL, String.class);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}