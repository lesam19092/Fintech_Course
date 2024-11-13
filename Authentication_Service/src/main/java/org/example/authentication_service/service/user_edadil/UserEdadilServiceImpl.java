package org.example.authentication_service.service.user_edadil;


import lombok.RequiredArgsConstructor;
import org.example.authentication_service.exception.CodeMismatchException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.Role;
import org.example.authentication_service.model.entity.UserEdadil;
import org.example.authentication_service.repository.UserEdadilRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEdadilServiceImpl implements UserEdadilService {
    private final UserEdadilRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEdadil findByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEdadil user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
    }

    @Override
    public UserEdadil createNewUser(RegistrationUserDto registrationUserDto) {
        UserEdadil user = new UserEdadil();
        user.setName(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByName(username).isPresent();
    }

    @Override
    public void resetPassword(PasswordResetRequest request) {
        if (!"0000".equals(request.getConfirmationCode())) {
            throw new CodeMismatchException("Invalid confirmation code");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        UserEdadil user = findByUsername(request.getName());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}