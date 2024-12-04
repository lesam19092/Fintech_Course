package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.ShopProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Long> {
    @EntityGraph(attributePaths = {"shop", "shop.nameOfCompany", "product", "product.firm"})
    List<ShopProduct> findShopProductsByProductId(Long productId);

    @Query("SELECT DISTINCT sp FROM ShopProduct sp JOIN FETCH sp.shop s JOIN FETCH s.nameOfCompany c JOIN FETCH sp.product p JOIN FETCH p.firm f WHERE f.id = :firmId AND c.id = :companyId")
    List<ShopProduct> findShopProductsByFirmIdAndCompanyId(Long firmId, Long companyId);

    @Query("SELECT sp FROM ShopProduct sp WHERE sp.shop.nameOfCompany.id = :companyId AND sp.shop.city = :city AND sp.shop.id = :shopId")
    List<ShopProduct> findShopProductsByCompanyIdAndCityAndShopId(Long companyId, String city, Long shopId);

    @Query("SELECT sp FROM ShopProduct sp JOIN FETCH sp.shop s JOIN FETCH s.nameOfCompany c JOIN FETCH sp.product p JOIN FETCH p.firm f WHERE f.id = :firmId AND c.id = :companyId AND s.id = :shopId")
    List<ShopProduct> findProductsInShopByFirmAndCompany(Long firmId, Long companyId, Long shopId);

    @Query("SELECT sp FROM ShopProduct sp WHERE sp.shop.id = :shopId")
    List<ShopProduct> findByShopId(Long shopId);
}