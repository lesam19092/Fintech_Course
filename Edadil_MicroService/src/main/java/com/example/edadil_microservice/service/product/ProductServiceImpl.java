package com.example.edadil_microservice.service.product;


import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return requireNonEmptyCollection(productRepository.findAll());
    }


}
