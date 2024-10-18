package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}