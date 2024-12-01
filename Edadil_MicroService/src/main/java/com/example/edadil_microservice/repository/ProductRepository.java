package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.firm.id = :firmId")
    List<Product> findByFirmId(Integer firmId);
}