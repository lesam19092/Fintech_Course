package org.example.authentication_service.service.user_foodru;

import lombok.RequiredArgsConstructor;
import org.example.authentication_service.exception.CodeMismatchException;
import org.example.authentication_service.exception.PasswordMismatchException;
import org.example.authentication_service.model.dto.PasswordResetRequest;
import org.example.authentication_service.model.dto.RegistrationUserDto;
import org.example.authentication_service.model.entity.Role;
import org.example.authentication_service.model.entity.UserFoodRu;
import org.example.authentication_service.repository.UserFoodRuRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserFoodRuServiceImpl implements UserFoodRuService {

    private final UserFoodRuRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserFoodRu findByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserFoodRu user = findByUsername(username);
        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getAuthority()))
        );
    }


    @Override
    public UserFoodRu createNewUser(RegistrationUserDto registrationUserDto) {
        UserFoodRu user = new UserFoodRu();
        user.setName(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRole(Role.USER);
        user.setEmail(registrationUserDto.getEmail());
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

        UserFoodRu user = findByUsername(request.getName());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}