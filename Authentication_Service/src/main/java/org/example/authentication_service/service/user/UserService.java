package org.example.authentication_service.service.user;

import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User createNewUser(RegistrationUserDto registrationUserDto);

    boolean existsByUsernameAndInstance(String username, String instanceName);

    void resetPassword(PasswordResetRequest request);


}