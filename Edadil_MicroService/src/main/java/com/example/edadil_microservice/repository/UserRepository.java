package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}

