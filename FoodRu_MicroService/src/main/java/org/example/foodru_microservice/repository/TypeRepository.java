package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}