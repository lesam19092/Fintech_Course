package org.example.foodru_microservice.service.user;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.model.entity.Role;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.UserRepository;
import org.example.foodru_microservice.service.jwt.JwtTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;


    @Override
    public void saveUser(String jwtToken) {
        String username = jwtTokenService.getUsername(jwtToken);
        if (userRepository.existsByName(username)) {
            return;
        }
        User user = createUserFromToken(jwtToken);
        userRepository.save(user);
    }

    private User createUserFromToken(String jwtToken) {

        User user = new User();
        user.setName(jwtTokenService.getUsername(jwtToken));

        System.out.println(Role.valueOf(jwtTokenService.getRole(jwtToken)));
        user.setRole(Role.valueOf(jwtTokenService.getRole(jwtToken)));
        user.setEmail(jwtTokenService.getEmail(jwtToken));

        return user;
    }
}
