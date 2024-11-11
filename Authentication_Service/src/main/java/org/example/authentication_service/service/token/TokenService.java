package org.example.authentication_service.service.token;

import org.example.authentication_service.model.entity.Token;
import org.example.authentication_service.model.entity.User;

public interface TokenService {

    Token saveToken(User user, String token);

    void revokeAllTokenByUser(User user);

    boolean isValid(String token);

}