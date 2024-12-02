package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.service.shop_product.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopProductController {

    private final ShopProductService shopProductService;


    @GetMapping("/companies/{companyId}/shops/{city}/{shopId}/products")
    public List<ShopProductDto> getCompanyShopProducts(@PathVariable Integer companyId, @PathVariable String city, @PathVariable Integer shopId) {
        return shopProductService.retrieveShopProducts(companyId, city, shopId);
    }

    @GetMapping("/firms/{firmId}/products/{productId}/shops")
    public List<ShopProductDto> getShopsWithProduct(@PathVariable Integer firmId, @PathVariable Integer productId) {
        return shopProductService.findShopsSellingProduct(firmId, productId);
    }


    /***
     получение продуктов магазина компании , у которых есть продукция фирмы
     */
    @GetMapping("/firms/{firmId}/company/{companyId}/shops/{shopId}/products")
    public List<ShopProductDto> findFirmProductsInShop(@PathVariable Integer firmId, @PathVariable Integer companyId, @PathVariable Integer shopId) {
        return shopProductService.findProductsInShopByFirmAndCompany(firmId, companyId, shopId);
    }


}
