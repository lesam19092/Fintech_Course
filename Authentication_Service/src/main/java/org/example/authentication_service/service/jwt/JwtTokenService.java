package org.example.authentication_service.service.jwt;


import org.example.authentication_service.model.entity.User;

public interface JwtTokenService {


    String generateToken(User user, Long minutes);


}