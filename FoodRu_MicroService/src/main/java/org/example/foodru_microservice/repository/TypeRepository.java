package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
}