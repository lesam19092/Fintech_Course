package com.example.edadil_microservice.service.shop_product;

import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.mapper.ShopProductMapper;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.repository.ShopProductRepository;
import com.example.edadil_microservice.service.shop.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopProductServiceImpl implements ShopProductService {

    private final ShopProductRepository shopProductRepository;
    private final ShopProductMapper shopProductMapper;
    private final ShopService shopService;


    @Override
    public ShopProductDto retrieveShopProducts(Integer companyId, String city, Integer shopId) {
        List<ShopProduct> shopProduct = shopProductRepository.findShopProductsByCompanyIdAndCityAndShopId(companyId, city, shopId);
        if (shopProduct.isEmpty()) {
            throw new EntityNotFoundException("Products not found");
        }
        log.info("Returning shop product response");
        return shopProductMapper.toShopProductDto(shopProduct);
    }

    @Override
    public List<ShopProductDto> findShopsSellingProduct(Integer firmId, Integer productId) {
        log.info("Fetching shops selling product with ID: {} for firm with ID: {}", productId, firmId);
        List<ShopProduct> shopProducts = shopProductRepository.findShopProductsByProductId(productId);
        if (shopProducts.isEmpty()) {
            throw new EntityNotFoundException("Shops not found");
        }
        return shopProducts.stream()
                .map(shopProductMapper::toShopProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShopProductDto findProductsInShopByFirmAndCompany(Integer firmId, Integer companyId, Integer shopId) {
        log.info("Fetching products in shop with ID: {} for firm with ID: {} and company with ID: {}", shopId, firmId, companyId);
        List<ShopProduct> shopProduct = shopProductRepository.findProductsInShopByFirmAndCompany(firmId, companyId, shopId);
        if (shopProduct.isEmpty()) {
            throw new EntityNotFoundException("Products not found");
        }
        return shopProductMapper.toShopProductDto(shopProduct);
    }


    @Override
    public List<ShopProductDto> getAllShopsWithProducts() {
        return shopService.getIdShops()
                .stream()
                .map(this::retrieveShopProducts)
                .toList();
    }

    private ShopProductDto retrieveShopProducts(Integer shopId) {
        List<ShopProduct> shopProduct = shopProductRepository.findByShopId(shopId);
        if (shopProduct.isEmpty()) {
            return null;
        }
        return shopProductMapper.toShopProductDto(shopProduct);
    }


}
