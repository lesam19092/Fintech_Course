package com.example.edadil_microservice.service.product;

import com.example.edadil_microservice.controller.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findProductsByFirmId(Long firmId);

    ProductDto findProductByIdAndFirmId(Long firmId, Long productId);
}