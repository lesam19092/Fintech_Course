package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
import com.example.edadil_microservice.mapper.ShopResponseMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.model.response.ShopResponse;
import com.example.edadil_microservice.repository.CompanyRepository;
import com.example.edadil_microservice.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final ShopRepository shopRepository;


    @Override
    public List<Company> getAllCompanies() {
        return getCollectionOrElseThrowException(companyRepository.findAll());
    }

    @Override
    public Company getCompanyById(Integer companyId) {
        return getEntityOrElseThrowException(companyRepository.findById(companyId));
    }

    @Override
    public Set<ShopResponse> getCompanyShops(Integer companyId) {
        Company company = getCompanyById(companyId);
        return ShopResponseMapper.mapShopsToShopResponses(company.getShops());
    }

    @Override
    public Set<ShopResponse> getCompanyShopsByCity(Integer companyId, String city) {
        Set<Shop> shops = shopRepository.findShopsByNameOfCompanyIdAndCity(companyId, city);
        return ShopResponseMapper.mapShopsToShopResponses(shops);
    }

    @Override
    public ShopResponse getCompanyShopByCityAndShopId(Integer companyId, String city, Integer shopId) {
        Set<ShopResponse> shops = getCompanyShopsByCity(companyId, city);
        Optional<ShopResponse> shop = shops.stream()
                .filter(s -> s.getId() == (shopId))
                .findFirst();
        return getEntityOrElseThrowException(shop);
    }


    @Override
    public ShopProductResponse getCompanyShopProducts(Integer companyId, String city, Integer shopId) {
        Optional<Shop> optionalShop = shopRepository.findShopByNameOfCompanyIdAndIdAndCity(companyId, shopId, city);
        Shop shop = getEntityOrElseThrowException(optionalShop);
        return ShopProductResponseMapper
                .mapShopToShopProductResponse(shop);
    }

    private static <T extends Collection<?>> T getCollectionOrElseThrowException(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        throw new NoSuchElementException("Empty collection");
    }

    private static <E> E getEntityOrElseThrowException(Optional<E> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new NoSuchElementException("Entity with id not found");

    }
}
