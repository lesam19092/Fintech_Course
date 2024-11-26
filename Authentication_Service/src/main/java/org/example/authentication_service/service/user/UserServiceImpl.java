package org.example.authentication_service.service.user;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.handler.exception.EmailNotFoundException;
import org.example.authentication_service.handler.exception.UserNotVerifiedException;
import org.example.authentication_service.model.entity.ConfirmationToken;
import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.UserRepository;
import org.example.authentication_service.service.confirm_token.ConfirmTokenService;
import org.example.authentication_service.service.email.EmailService;
import org.example.authentication_service.service.instance.InstanceService;
import org.example.authentication_service.service.password_token.PasswordResetTokenService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.example.authentication_service.model.entity.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InstanceService instanceService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmTokenService confirmTokenService;
    private final EmailService emailService;
    private final PasswordResetTokenService passwordResetTokenService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameAndInstance) throws UsernameNotFoundException {
        String[] parts = splitUsername(usernameAndInstance);
        String actualUsername = parts[0];
        String userType = parts[1];

        User user = findUserByNameAndInstance(actualUsername, userType);
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
    }

    @Override
    public boolean existsByEmailAndInstance(String email, String instanceName) {
        return userRepository.findByMailAndInstance(email, instanceName).isPresent();
    }

    @Override
    public void createNewUser(RegistrationUserDto registrationUserDto) {
        Instance instance = instanceService.getByName(registrationUserDto.getUserType().name());

        User user = buildUserFromDto(registrationUserDto, instance);
        saveUserWithConfirmToken(user);

        try {
            emailService.sendEmailWithVerification(
                    registrationUserDto.getEmail(),
                    user.getConfirmationToken().getToken()
            );
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }

    @Override
    public boolean existsByUsernameAndInstance(String username, String instanceName) {
        return userRepository.findByUsernameAndInstance(username, instanceName).isPresent();
    }


    @Override
    public Boolean isEmailConfirmed(String confirmationToken) {
        ConfirmationToken token = confirmTokenService.findByConfirmationToken(confirmationToken);
        User user = token.getUser();

        if (user.isEnable()) {
            return false;
        }

        user.setEnable(true);
        userRepository.save(user);
        return true;
    }


    @Override
    public boolean isVerified(String username, String instanceName) {
        return findUserByNameAndInstance(username, instanceName).isEnable();
    }

    @Override
    public void updatePassword(User user, String password) {
        user.getPasswordResetToken().setUsed(true);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


    @Override
    public void resetPassword(PasswordResetRequest request) throws MessagingException {
        User user = findByMailAndInstance(request.getEmail(), request.getUserType());
        validateUserIsEnabled(user);
        String token = generatePasswordResetToken(user);
        emailService.sendEmailWithRestorePassword(request.getEmail(), token);
    }

    @Override
    public User findUserByNameAndInstance(String username, String instanceName) {
        return userRepository.findByUsernameAndInstance(username, instanceName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findByMailAndInstance(String mail, String instanceName) {
        return userRepository.findByMailAndInstance(mail, instanceName)
                .orElseThrow(() -> new EmailNotFoundException("User not found with email: " + mail + " and instance: " + instanceName));

    }

    @Override
    public User findUserByPasswordToken(String passwordToken) {
        return userRepository.findUserByPasswordResetToken(passwordToken)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private User buildUserFromDto(RegistrationUserDto registrationUserDto, Instance instance) {
        return User.builder()
                .instance(instance)
                .name(registrationUserDto.getUsername())
                .email(registrationUserDto.getEmail())
                .enable(false)
                .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                .role(ROLE_USER)
                .build();
    }

    private String[] splitUsername(String username) {
        String[] parts = username.split(":");
        if (parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format");
        }
        return parts;
    }

    private void saveUserWithConfirmToken(User user) {
        String confirmToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(confirmToken, user);

        confirmTokenService.saveConfirmToken(confirmationToken);
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);
    }

    private void validateUserIsEnabled(User user) {
        if (!user.isEnable()) {
            throw new UserNotVerifiedException("User not verified");
        }
    }

    private String generatePasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        if (user.getPasswordResetToken() != null) {
            passwordResetTokenService.delete(user.getPasswordResetToken());
        }
        passwordResetTokenService.save(token, user);
        return token;
    }

}