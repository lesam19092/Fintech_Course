package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p JOIN FETCH p.firm f WHERE f.id = :firmId AND p.id = :productId")
    Optional<Product> findProductByIdAndFirmId(@Param("firmId") Integer firmId, @Param("productId") Integer productId);
}