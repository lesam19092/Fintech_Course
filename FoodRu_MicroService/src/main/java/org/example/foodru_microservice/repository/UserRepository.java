package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}