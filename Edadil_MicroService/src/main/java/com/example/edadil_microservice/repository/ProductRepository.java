package com.example.edadil_microservice.repository;

import com.example.edadil_microservice.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}