package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.UserFoodRu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFoodRuRepository extends JpaRepository<UserFoodRu, Integer> {
    Optional<UserFoodRu> findByName(String name);

}