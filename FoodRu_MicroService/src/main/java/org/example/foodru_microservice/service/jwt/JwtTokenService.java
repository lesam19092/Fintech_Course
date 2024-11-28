package org.example.foodru_microservice.service.jwt;

import java.util.List;

public interface JwtTokenService {


    List<String> getRoles(String token);

    String getUsername(String token);


}