package org.example.authentication_service.service.user;


import lombok.RequiredArgsConstructor;
import org.example.authentication_service.exception.CodeMismatchException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.Instance;
import org.example.authentication_service.model.entity.User;
import org.example.authentication_service.repository.UserRepository;
import org.example.authentication_service.service.instance.InstanceService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.authentication_service.model.entity.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final InstanceService instanceService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
    }


    @Override
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        Instance instance = instanceService.getByName(registrationUserDto.getUserType().name());
        User user = buildUserFromDto(registrationUserDto, instance);
        return userRepository.save(user);
    }


    @Override
    public boolean existsByUsernameAndInstance(String username, String instanceName) {
        return userRepository.findByUsernameAndInstance(username, instanceName).isPresent();
    }


    //todo поменять логику
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

    private User buildUserFromDto(RegistrationUserDto registrationUserDto, Instance instance) {
        User user = new User();
        user.setInstance(instance);
        user.setName(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(ROLE_USER);
        return user;
    }
}