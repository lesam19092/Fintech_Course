package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.UsersMeal;
import org.example.foodru_microservice.model.entity.UsersMealId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersMealRepository extends JpaRepository<UsersMeal, UsersMealId> {


    @Query("SELECT um FROM UsersMeal um JOIN FETCH um.meal WHERE um.user.id = :userId")
    List<UsersMeal> findByUserId(Long userId);
}