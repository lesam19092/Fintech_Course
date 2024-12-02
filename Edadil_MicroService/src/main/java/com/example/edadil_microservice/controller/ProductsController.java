package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {


    private final ProductService productService;


    @GetMapping("/firms/{firmId}/products")
    public List<ProductDto> getFirmProducts(@PathVariable Integer firmId) {
        return productService.findProductsByFirmId(firmId);
    }

    @GetMapping("/firms/{firmId}/products/{productId}")
    public ProductDto getFirmProductById(@PathVariable Integer firmId, @PathVariable Integer productId) {
        return productService.findProductByIdAndFirmId(firmId, productId);
    }


}
