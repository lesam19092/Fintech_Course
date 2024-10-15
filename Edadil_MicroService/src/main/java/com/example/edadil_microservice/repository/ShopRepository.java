package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Integer> {

    Set<Shop> findShopsByNameOfCompanyIdAndCity(Integer companyId, String city);

    Optional<Shop> findShopByNameOfCompanyIdAndIdAndCity(Integer companyId, Integer shopId, String city);

    Optional<Shop> findShopByNameOfCompanyIdAndId(Integer companyId, Integer shopId);

}