package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {
}