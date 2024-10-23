package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.ShopProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {
    @EntityGraph(attributePaths = {"shop", "shop.nameOfCompany", "product", "product.firm"})
    Set<ShopProduct> findShopProductsByProduct_Id(Integer productId);

    @Query("SELECT sp FROM ShopProduct sp JOIN FETCH sp.shop s JOIN FETCH s.nameOfCompany c JOIN FETCH sp.product p JOIN FETCH p.firm f WHERE f.id = :firmId AND c.id = :companyId")
    Set<ShopProduct> findShopProductsByFirmIdAndCompanyId(@Param("firmId") Integer firmId, @Param("companyId") Integer companyId);
}