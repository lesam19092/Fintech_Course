package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c WHERE c.id = :companyId AND s.city = :city")
    Set<Shop> findShopsByCompanyIdAndCity(Integer companyId, String city);

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c WHERE c.id = :companyId AND s.city = :city and s.id = :shopId")
    Optional<Shop> findShopByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId);

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c JOIN FETCH s.shopProducts p JOIN FETCH p.product WHERE c.id = :companyId AND s.city = :city AND s.id = :shopId")
    Optional<Shop> findShopProductsByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId);

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c WHERE c.id = :companyId  and s.id = :shopId")
    Optional<Shop> findShopByNameOfCompanyIdAndId(Integer companyId, Integer shopId);


}