package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String username);

    Optional<User> findByName(String username);
}