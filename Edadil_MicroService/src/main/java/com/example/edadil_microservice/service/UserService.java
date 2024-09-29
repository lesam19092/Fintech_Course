package com.example.edadil_microservice.service;

import com.example.edadil_microservice.entity.User;

import java.util.Optional;

public interface UserService {
    void register(String username, String password);

    Optional<User> login(String username, String password);

    Optional<User> findByUsername(String username);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);

    void deleteByUsername(String username);
}
