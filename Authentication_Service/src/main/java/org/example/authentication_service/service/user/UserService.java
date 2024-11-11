package main.java.org.example.authentication_service.service.user;

import main.java.org.example.authentication_service.model.entity.User;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User createNewUser(RegistrationUserDto registrationUserDto);

    boolean existsByUsername(String username);

    void resetPassword(PasswordResetRequest request);


}