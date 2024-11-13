package org.example.authentication_service.strategy;

import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.service.auth.AuthService;

public interface AuthenticationStrategy {

    AuthService getStrategy(UserType userType);


}
