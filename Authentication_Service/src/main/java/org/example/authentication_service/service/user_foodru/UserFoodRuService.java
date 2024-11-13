package org.example.authentication_service.service.user_foodru;

import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.UserFoodRu;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserFoodRuService extends UserDetailsService {

    UserFoodRu findByUsername(String username);

    UserFoodRu createNewUser(RegistrationUserDto registrationUserDto);

    boolean existsByUsername(String username);

    void resetPassword(PasswordResetRequest request);


}