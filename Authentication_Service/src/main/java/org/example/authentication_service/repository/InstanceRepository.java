package org.example.authentication_service.repository;

import org.example.authentication_service.model.entity.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstanceRepository extends JpaRepository<Instance, Integer> {

    Optional<Instance> findByName(String name);
}