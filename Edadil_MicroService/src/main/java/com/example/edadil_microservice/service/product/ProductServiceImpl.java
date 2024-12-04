package com.example.edadil_microservice.service.product;


import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.mapper.ProductMapper;
import com.example.edadil_microservice.model.entity.Product;
import com.example.edadil_microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findProductsByFirmId(Long firmId) {
        log.info("Fetching products for firm with ID: {}", firmId);
        List<Product> products = productRepository.findByFirmId(firmId);
        checkNonEmptyCollection(products);
        return productMapper.toDtoList(products);
    }

    @Override
    public ProductDto findProductByIdAndFirmId(Long firmId, Long productId) {
        log.info("Fetching product with ID: {} for firm with ID: {}", productId, firmId);
        Product product = productRepository.findByFirmIdAndProductId(firmId, productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId + " for firm ID: " + firmId));
        return productMapper.toDto(product);
    }

    private void checkNonEmptyCollection(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new EntityNotFoundException("No products found for the given firm ID");
        }
    }


}

