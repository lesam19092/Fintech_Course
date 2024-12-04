package org.example.foodru_microservice.service.jwt;

public interface JwtTokenService {


    String getRole(String token);

    String getUsername(String token);

    String getEmail(String token);


}