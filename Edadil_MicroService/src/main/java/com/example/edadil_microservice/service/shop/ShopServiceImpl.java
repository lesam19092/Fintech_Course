package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.mapper.ShopMapper;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.entity.ShopProduct;
import com.example.edadil_microservice.repository.ShopProductRepository;
import com.example.edadil_microservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopProductRepository shopProductRepository;
    private final ShopMapper shopMapper;

    @Override
    public List<ShopDto> findCompanyShops(Integer companyId) {
        log.info("Fetching shops for company with ID: {}", companyId);
        List<Shop> shops = getShopsByCompanyId(companyId);
        return shopMapper.toDtoList(shops);
    }

    @Override
    public List<ShopDto> findCompanyShopsInCity(Integer companyId, String city) {
        log.info("Fetching shops for company with ID: {} in city: {}", companyId, city);
        List<Shop> shops = findShopsByCompanyIdAndCity(companyId, city);
        return shopMapper.toDtoList(shops);
    }

    @Override
    public ShopDto findCompanyShopInCityById(Integer companyId, String city, Integer shopId) {
        log.info("Fetching shop with ID: {} in city: {} for company with ID: {}", shopId, city, companyId);
        Shop shop = findShopByCompanyIdAndCityAndId(companyId, city, shopId);
        return shopMapper.toDto(shop);
    }


    @Override
    public ShopDto findShopInCompanyWithFirmProductsById(Integer firmId, Integer companyId, Integer shopId) {
        log.info("Fetching shop with ID: {} in company with ID: {} selling products for firm with ID: {}", shopId, companyId, firmId);
        return findShopsInCompanyWithFirmProducts(firmId, companyId).stream()
                .filter(shop -> shop.getId().equals(shopId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Shop not found with ID: " + shopId + " in company with ID: " + companyId + " selling products for firm with ID: " + firmId));
    }

    @Override
    public List<ShopDto> findShopsInCompanyWithFirmProducts(Integer firmId, Integer companyId) {
        log.info("Fetching shops in company with ID: {} selling products for firm with ID: {}", companyId, firmId);

        List<ShopProduct> shops = getUniqueShopProductsByFirmAndCompany(firmId, companyId);

        if (shops.isEmpty()) {
            throw new EntityNotFoundException("No shops found in company with ID: " + companyId + " selling products for firm with ID: " + firmId);
        }
        return shops.stream()
                .map(shopProduct -> shopMapper.toDto(shopProduct.getShop()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> getIdShops() {
        List<Shop> shops = shopRepository.findAll();

        if (shops.isEmpty()) {
            throw new EntityNotFoundException("No shops found");
        }

        return shops.stream()
                .map(Shop::getId)
                .toList();
    }


    private List<Shop> getShopsByCompanyId(Integer companyId) {
        List<Shop> shops = shopRepository.findShopsByCompanyId(companyId);
        if (shops.isEmpty()) {
            throw new EntityNotFoundException("No shops found for company ID: " + companyId);
        }
        return shops;
    }

    private Shop findShopByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId) {
        return shopRepository.findShopByCompanyIdAndCityAndId(companyId, city, shopId)
                .orElseThrow(() -> new EntityNotFoundException("Shop not found for company ID: " + companyId + ", city: " + city + ", shop ID: " + shopId));
    }

    private List<Shop> findShopsByCompanyIdAndCity(Integer companyId, String city) {
        List<Shop> shops = shopRepository.findShopsByCompanyIdAndCity(companyId, city);
        if (shops.isEmpty()) {
            throw new EntityNotFoundException("No shops found for company ID: " + companyId + " in city: " + city);
        }
        return shops;
    }

    private List<ShopProduct> getUniqueShopProductsByFirmAndCompany(Integer firmId, Integer companyId) {
        List<ShopProduct> shopProducts = shopProductRepository.findShopProductsByFirmIdAndCompanyId(firmId, companyId);

        Map<Integer, ShopProduct> uniqueShopProductsMap = shopProducts.stream()
                .collect(Collectors.toMap(
                        sp -> sp.getId().getShopId(),
                        sp -> sp,
                        (existing, replacement) -> existing
                ));

        return new ArrayList<>(uniqueShopProductsMap.values());
    }


}
