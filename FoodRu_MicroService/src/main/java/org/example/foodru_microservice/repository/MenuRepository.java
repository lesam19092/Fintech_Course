package org.example.foodru_microservice.repository;

import org.example.foodru_microservice.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}