package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {

    Set<ShopProduct> findShopProductsByProduct_Id(Integer productId);
}