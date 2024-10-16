    package com.example.edadil_microservice.service.company;

    import com.example.edadil_microservice.mapper.ShopProductResponseMapper;
    import com.example.edadil_microservice.mapper.ShopResponseMapper;
    import com.example.edadil_microservice.model.entity.Company;
    import com.example.edadil_microservice.model.entity.Shop;
    import com.example.edadil_microservice.model.response.ProductResponse;
    import com.example.edadil_microservice.model.response.ShopProductResponse;
    import com.example.edadil_microservice.model.response.ShopResponse;
    import com.example.edadil_microservice.repository.CompanyRepository;
    import com.example.edadil_microservice.repository.ShopRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.Set;

    import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
    import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class CompanyServiceImpl implements CompanyService {

        private final CompanyRepository companyRepository;
        private final ShopRepository shopRepository;

        @Override
        public List<Company> findAllCompanies() {
            log.info("Fetching all companies");
            return requireNonEmptyCollection(companyRepository.findAll());
        }

        @Override
        public Company findCompanyById(Integer companyId) {
            log.info("Fetching company with ID: {}", companyId);
            return requirePresentEntity(companyRepository.findById(companyId));
        }

        @Override
        public Set<ShopResponse> findCompanyShops(Integer companyId) {
            log.info("Fetching shops for company with ID: {}", companyId);
            Company company = findCompanyById(companyId);
            return ShopResponseMapper.convertShopsToShopResponses(company.getShops());
        }

        @Override
        public Set<ShopResponse> findCompanyShopsInCity(Integer companyId, String city) {
            log.info("Fetching shops for company with ID: {} in city: {}", companyId, city);
            Set<Shop> shops = shopRepository.findShopsByNameOfCompanyIdAndCity(companyId, city);
            return ShopResponseMapper.convertShopsToShopResponses(shops);
        }

        @Override
        public ShopResponse findCompanyShopInCityById(Integer companyId, String city, Integer shopId) {
            log.info("Fetching shop with ID: {} in city: {} for company with ID: {}", shopId, city, companyId);
            Set<ShopResponse> shops = findCompanyShopsInCity(companyId, city);
            Optional<ShopResponse> shop = shops.stream()
                    .filter(s -> s.getId() == (shopId))
                    .findFirst();
            return requirePresentEntity(shop);
        }

        @Override
        public ShopProductResponse retrieveShopProducts(Integer companyId, String city, Integer shopId) {
            log.info("Fetching products for shop with ID: {} in city: {} for company with ID: {}", shopId, city, companyId);
            Optional<Shop> optionalShop = shopRepository.findShopByNameOfCompanyIdAndIdAndCity(companyId, shopId, city);
            Shop shop = requirePresentEntity(optionalShop);
            return ShopProductResponseMapper.buildShopProductResponse(shop);
        }

        @Override
        public ShopProductResponse retrieveShopProducts(Integer companyId, Integer shopId) {
            log.info("Fetching products for shop with ID: {} for company with ID: {}", shopId, companyId);
            Optional<Shop> optionalShop = shopRepository.findShopByNameOfCompanyIdAndId(companyId, shopId);
            Shop shop = requirePresentEntity(optionalShop);
            return ShopProductResponseMapper.buildShopProductResponse(shop);
        }

        @Override
        public ShopProductResponse findSpecificProductInShop(Integer companyId, String city, Integer shopId, String name) {
            log.info("Fetching specific product: {} for shop with ID: {} in city: {} for company with ID: {}", name, shopId, city, companyId);
            ShopProductResponse shopProductResponse = retrieveShopProducts(companyId, city, shopId);
            Optional<ProductResponse> product = shopProductResponse.getProducts().stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst();
            shopProductResponse.setProducts(Set.of(requirePresentEntity(product)));
            return shopProductResponse;
        }

        @Override
        public List<ShopProductResponse> getAllShopsWithProducts() {
            List<Company> list = findAllCompanies();
            log.debug("Found {} companies", list.size());
            List<ShopProductResponse> shopProductResponses = new ArrayList<>();
            list.forEach(company -> {
                findCompanyShops(company.getId()).forEach(shopResponse -> {
                    log.debug("Processing shop with ID: {} for company with ID: {}", shopResponse.getId(), company.getId());
                    ShopProductResponse shopProductResponse = retrieveShopProducts(company.getId(), shopResponse.getId());
                    shopProductResponses.add(shopProductResponse);
                });
            });
            log.info("Returning {} shop product responses", shopProductResponses.size());
            return shopProductResponses;
        }
    }