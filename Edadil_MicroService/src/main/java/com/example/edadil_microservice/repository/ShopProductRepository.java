package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.ShopProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer> {
    @EntityGraph(attributePaths = {"shop", "shop.nameOfCompany", "product", "product.firm"})
    List<ShopProduct> findShopProductsByProductId(Integer productId);

    @Query("SELECT DISTINCT sp FROM ShopProduct sp JOIN FETCH sp.shop s JOIN FETCH s.nameOfCompany c JOIN FETCH sp.product p JOIN FETCH p.firm f WHERE f.id = :firmId AND c.id = :companyId")
    List<ShopProduct> findShopProductsByFirmIdAndCompanyId(Integer firmId, Integer companyId);

    @Query("SELECT sp FROM ShopProduct sp WHERE sp.shop.nameOfCompany.id = :companyId AND sp.shop.city = :city AND sp.shop.id = :shopId")
    List<ShopProduct> findShopProductsByCompanyIdAndCityAndShopId(Integer companyId, String city, Integer shopId);

    @Query("SELECT sp FROM ShopProduct sp JOIN FETCH sp.shop s JOIN FETCH s.nameOfCompany c JOIN FETCH sp.product p JOIN FETCH p.firm f WHERE f.id = :firmId AND c.id = :companyId AND s.id = :shopId")
    List<ShopProduct> findProductsInShopByFirmAndCompany(Integer firmId, Integer companyId, Integer shopId);

    @Query("SELECT sp FROM ShopProduct sp WHERE sp.shop.id = :shopId")
    List<ShopProduct> findByShopId(Integer shopId);
}