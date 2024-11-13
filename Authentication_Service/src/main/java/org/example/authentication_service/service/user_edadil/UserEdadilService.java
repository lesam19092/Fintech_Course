package org.example.authentication_service.service.user_edadil;

import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.UserEdadil;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserEdadilService extends UserDetailsService {

    UserEdadil findByUsername(String username);

    UserEdadil createNewUser(RegistrationUserDto registrationUserDto);

    boolean existsByUsername(String username);

    void resetPassword(PasswordResetRequest request);


}