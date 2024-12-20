package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.api.ShopProductApi;
import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints;
import com.example.edadil_microservice.service.shop_product.ShopProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints.GET_COMPANY_SHOP_PRODUCTS;

@RestController
@RequiredArgsConstructor
public class ShopProductController implements ShopProductApi {

    private final ShopProductService shopProductService;


    @GetMapping(GET_COMPANY_SHOP_PRODUCTS)
    public ShopProductDto getCompanyShopProducts(@PathVariable Long companyId, @PathVariable String city, @PathVariable Long shopId) {
        return shopProductService.retrieveShopProducts(companyId, city, shopId);
    }

    @GetMapping(ShopProductEndpoints.GET_SHOPS_WITH_PRODUCT)
    public List<ShopProductDto> getShopsWithProduct(@PathVariable Long firmId, @PathVariable Long productId) {
        return shopProductService.findShopsSellingProduct(firmId, productId);
    }

    @GetMapping(ShopProductEndpoints.GET_FIRM_PRODUCTS_IN_SHOP)
    public ShopProductDto findFirmProductsInShop(@PathVariable Long firmId, @PathVariable Long companyId, @PathVariable Long shopId) {
        return shopProductService.findProductsInShopByFirmAndCompany(firmId, companyId, shopId);
    }



}
