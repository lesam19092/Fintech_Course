package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    //TODO ЗАПОЛНИТЬ ТАБЛИЦУ ДАННЫМИ

    private final CompanyService companyService;


    public List<ShopProductResponse> getAllShopsWithProducts() {

        List<Company> list = companyService.findAllCompanies();
        List<ShopProductResponse> shopProductResponses = new ArrayList<>();
        list.forEach(company -> {
            companyService.findCompanyShops(company.getId()).forEach(shopResponse -> {
                ShopProductResponse shopProductResponse = companyService
                        .retrieveShopProducts(company.getId(), shopResponse.getId());
                shopProductResponses.add(shopProductResponse);
            });
        });

        return shopProductResponses;
    }

  /*  public PaymentReceipt generatePaymentReceipt(List<ShopProductResponse> products) {
        PaymentReceipt receipt = new PaymentReceipt();

        products.forEach(product -> {
            // Проверка наличия продукта и достаточного количества
            if (!productRepository.isProductAvailable(product.getId(), product.getQuantity())) {
                receipt.addError("Недостаточное количество продукта: " + product.getName());
                return; // Переходим к следующему продукту
            }

            // Проверка цены (можно сравнить с ценой в базе данных)
            double actualPrice = productRepository.getProductPrice(product.getId());
            if (actualPrice != product.getPrice()) {
                receipt.addError("Неверная цена для продукта: " + product.getName());
            }

            // ... другие проверки

            // Добавление продукта в чек
            receipt.addProduct(product);
        });

        // Расчет итоговой суммы, добавление налогов и т.д.
        // ...

        return receipt;
    }*/





}
