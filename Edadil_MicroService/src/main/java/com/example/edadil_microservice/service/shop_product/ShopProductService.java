package com.example.edadil_microservice.service.shop_product;

import com.example.edadil_microservice.controller.dto.ShopProductDto;

import java.util.List;

public interface ShopProductService {
    ShopProductDto retrieveShopProducts(Integer companyId, String city, Integer shopId);

    List<ShopProductDto> findShopsSellingProduct(Integer firmId, Integer productId);

    ShopProductDto findProductsInShopByFirmAndCompany(Integer firmId, Integer companyId, Integer shopId);

    List<ShopProductDto> getAllShopsWithProducts();

}
