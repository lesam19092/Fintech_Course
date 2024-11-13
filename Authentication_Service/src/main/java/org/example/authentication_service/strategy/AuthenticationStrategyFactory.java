package org.example.authentication_service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.model.consts.UserType;
import org.example.authentication_service.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthenticationStrategyFactory implements AuthenticationStrategy {

    @Qualifier("authFoodRuServiceImpl")
    private final AuthService authFoodRuServiceImpl;
    @Qualifier("authEdadilServiceImpl")
    private final AuthService authEdadilServiceImpl;


    @Override
    public AuthService getStrategy(UserType userType) {
        return switch (userType) {
            case FoodRu -> authFoodRuServiceImpl;
            case Edadil -> authEdadilServiceImpl;
            default -> throw new IllegalArgumentException("Unknown user type");
        };
    }
}
