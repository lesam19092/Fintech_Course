package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.firm.id = :firmId")
    List<Product> findByFirmId(Long firmId);

    @Query("SELECT p FROM Product p WHERE p.firm.id = :firmId AND p.id = :productId")
    Optional<Product> findByFirmIdAndProductId(Long firmId, Long productId);
}
