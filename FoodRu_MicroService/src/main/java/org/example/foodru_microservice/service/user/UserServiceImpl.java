package org.example.foodru_microservice.service.user;

import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.repository.UserRepository;
import org.example.foodru_microservice.service.jwt.JwtTokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenService jwtTokenService;


    @Override
    public void saveUser(String jwtToken) {
        String username = jwtTokenService.getUsername(jwtToken);
        List<String> roles = jwtTokenService.getRoles(jwtToken);
        User user = new User();
        user.setUsername(username);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
