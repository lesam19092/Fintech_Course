package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}