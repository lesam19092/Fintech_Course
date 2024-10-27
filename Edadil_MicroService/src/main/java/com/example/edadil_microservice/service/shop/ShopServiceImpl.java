package com.example.edadil_microservice.service.shop;

import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public Set<Shop> findShopsByCompanyIdAndCity(Integer companyId, String city) {
        return requireNonEmptyCollection(
                shopRepository.findShopsByCompanyIdAndCity(companyId, city)
        );
    }

    @Override
    public Shop findShopByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId) {
        return requirePresentEntity(
                shopRepository.findShopByCompanyIdAndCityAndId(companyId, city, shopId)
        );
    }

    @Override
    public Shop findShopProductsByCompanyIdAndCityAndId(Integer companyId, String city, Integer shopId) {
        return requirePresentEntity(
                shopRepository.findShopProductsByCompanyIdAndCityAndId(companyId, city, shopId)
        );
    }

    @Override
    public Shop findShopByNameOfCompanyIdAndId(Integer companyId, Integer shopId) {
        return requirePresentEntity(
                shopRepository.findShopByNameOfCompanyIdAndId(companyId, shopId)
        );
    }
}
