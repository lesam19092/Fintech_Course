package org.example.authentication_service.service.user;

import jakarta.mail.MessagingException;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {


    void createNewUser(RegistrationUserDto registrationUserDto);

    boolean existsByUsernameAndInstance(String username, String instanceName);

    void resetPassword(PasswordResetRequest request) throws MessagingException;

    User findUserByNameAndInstance(String username, String instanceName);

    boolean existsByEmailAndInstance(String email, String instanceName);

    Boolean isEmailConfirmed(String confirmationToken);

    boolean isVerified(String username, String instanceName);

    void updatePassword(User user, String password) throws MessagingException;

    User findByMailAndInstance(String mail, String instanceName);

    User findUserByPasswordToken(String passwordToken);
}