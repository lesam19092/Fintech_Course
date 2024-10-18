package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Firm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmRepository extends JpaRepository<Firm, Integer> {
}