package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c WHERE c.id = :companyId AND s.city = :city")
    List<Shop> findShopsByCompanyIdAndCity(Long companyId, String city);

    @Query("SELECT s FROM Shop s JOIN FETCH s.nameOfCompany c WHERE c.id = :companyId AND s.city = :city AND s.id = :shopId")
    Optional<Shop> findShopByCompanyIdAndCityAndId(Long companyId, String city, Long shopId);

    @Query("SELECT s FROM Shop s WHERE s.nameOfCompany.id = :companyId")
    List<Shop> findShopsByCompanyId(Long companyId);
}
