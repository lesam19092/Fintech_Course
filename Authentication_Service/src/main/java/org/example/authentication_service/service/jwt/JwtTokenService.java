package org.example.authentication_service.service.jwt;


import org.example.authentication_service.model.entity.User;

import java.util.List;

public interface JwtTokenService {


    String generateToken(User user, String instanceName, long minutes);

    List<String> getRoles(String token);

    String getUsername(String token);

    boolean isValid(String token);


}