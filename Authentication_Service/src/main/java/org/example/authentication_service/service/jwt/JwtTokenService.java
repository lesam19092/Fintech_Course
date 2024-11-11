package main.java.org.example.authentication_service.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JwtTokenService {


    String generateToken(UserDetails userDetails, long minutes);

    List<String> getRoles(String token);

    String getUsername(String token);

    boolean isValid(String token);


}