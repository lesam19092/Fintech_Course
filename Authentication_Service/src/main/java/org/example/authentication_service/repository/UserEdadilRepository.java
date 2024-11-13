package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.UserEdadil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEdadilRepository extends JpaRepository<UserEdadil, Integer> {
    Optional<UserEdadil> findByName(String name);

}