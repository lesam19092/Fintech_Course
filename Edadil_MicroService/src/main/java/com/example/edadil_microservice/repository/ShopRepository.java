package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Integer> {

    Set<Shop> findShopsByNameOfCompanyAndCity(Company company, String city);

    Optional<Shop> findShopByNameOfCompanyAndIdAndCity(Company company, Integer shopId, String city);

}