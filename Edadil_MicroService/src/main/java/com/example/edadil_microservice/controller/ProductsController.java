package com.example.edadil_microservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductsController {


    @GetMapping("/products")
    public String getAllProducts() {
        return "All products";
    }


}
