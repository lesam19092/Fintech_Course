package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.UsersMeal;
import org.example.foodru_microservice.model.UsersMealId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersMealRepository extends JpaRepository<UsersMeal, UsersMealId> {
}