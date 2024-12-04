package com.example.edadil_microservice.service.shop_product;

import com.example.edadil_microservice.controller.dto.ShopProductDto;

import java.util.List;

public interface ShopProductService {
    ShopProductDto retrieveShopProducts(Long companyId, String city, Long shopId);

    List<ShopProductDto> findShopsSellingProduct(Long firmId, Long productId);

    ShopProductDto findProductsInShopByFirmAndCompany(Long firmId, Long companyId, Long shopId);

    List<ShopProductDto> getAllShopsWithProducts();

}

