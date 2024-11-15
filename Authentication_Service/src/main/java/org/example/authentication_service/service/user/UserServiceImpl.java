package org.example.authentication_service.service.user;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.authentication_service.controller.dto.PasswordResetRequest;
import org.example.authentication_service.controller.dto.RegistrationUserDto;
import org.example.authentication_service.exception.CodeMismatchException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.model.entity.ConfirmationToken;
import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.UserRepository;
import org.example.authentication_service.service.confirm_token.ConfirmTokenService;
import org.example.authentication_service.service.email.EmailService;
import org.example.authentication_service.service.instance.InstanceService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InstanceService instanceService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmTokenService confirmTokenService;
    private final EmailService emailService;


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

    @SneakyThrows
    @Override
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        Instance instance = instanceService.getByName(registrationUserDto.getUserType().name());

        User user = buildUserFromDto(registrationUserDto, instance);
        saveUserWithConfirmToken(user);

        emailService.sendEmail(registrationUserDto.getEmail(), user.getConfirmationToken().getConfirmationToken());

        return user;
    }

    @Override
    public boolean existsByUsernameAndInstance(String username, String instanceName) {
        return userRepository.findByUsernameAndInstance(username, instanceName).isPresent();
    }


    @Override
    public Boolean isEmailConfirmed(String confirmationToken) {
        ConfirmationToken token = confirmTokenService.findByConfirmationToken(confirmationToken);
        User user = token.getUser();

        if (!user.isEnable()) {
            user.setEnable(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    @Override
    public boolean isVerified(String username, String instanceName) {
        return findUserByNameAndInstance(username, instanceName).isEnable();
    }


    //todo поменять логику
    //todo не рефакторил

    @Override
    public void resetPassword(PasswordResetRequest request) {
        if (!"0000".equals(request.getConfirmationCode())) {
            throw new CodeMismatchException("Invalid confirmation code");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        User user = findByUsername(request.getName());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserByNameAndInstance(String username, String instanceName) {
        return userRepository.findByUsernameAndInstance(username, instanceName)
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

    private User findByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void saveUserWithConfirmToken(User user) {
        String confirmToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(confirmToken, user);

        confirmTokenService.saveConfirmToken(confirmationToken);
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);
    }

}