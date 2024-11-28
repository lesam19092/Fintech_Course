package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByName(String username);

    User findByName(String username);
}