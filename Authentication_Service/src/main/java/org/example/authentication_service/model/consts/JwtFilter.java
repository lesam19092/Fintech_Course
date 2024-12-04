package org.example.authentication_service.model.consts;

public interface JwtFilter {

    int BEARER_PREFIX_LENGTH = 7;
    String BEARER_PREFIX = "Bearer ";
    String AUTHORIZATION_HEADER = "Authorization";
}
