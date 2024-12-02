package com.example.edadil_microservice.controller;


import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ProductsEndpoint.GET_FIRM_PRODUCTS;
import static com.example.edadil_microservice.model.consts.endpoints.ProductsEndpoint.GET_FIRM_PRODUCT_BY_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {


    private final ProductService productService;


    @GetMapping(GET_FIRM_PRODUCTS)
    public List<ProductDto> getFirmProducts(@PathVariable Integer firmId) {
        return productService.findProductsByFirmId(firmId);
    }

    @GetMapping(GET_FIRM_PRODUCT_BY_ID)
    public ProductDto getFirmProductById(@PathVariable Integer firmId, @PathVariable Integer productId) {
        return productService.findProductByIdAndFirmId(firmId, productId);
    }


}
