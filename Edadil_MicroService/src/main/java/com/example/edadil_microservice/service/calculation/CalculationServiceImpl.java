package com.example.edadil_microservice.service.calculation;

import com.example.edadil_microservice.exception.EmptyResultException;
import com.example.edadil_microservice.model.request.IngredientRequest;
import com.example.edadil_microservice.model.response.IngredientResponse;
import com.example.edadil_microservice.model.response.PaymentReceipt;
import com.example.edadil_microservice.model.response.ProductResponse;
import com.example.edadil_microservice.model.response.ShopProductResponse;
import com.example.edadil_microservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {


    //TODO ЗАПОЛНИТЬ ТАБЛИЦУ ДАННЫМИ


    private final CompanyService companyService;


    @Override
    public List<PaymentReceipt> generatePaymentReceipt(List<IngredientRequest> response) throws IOException {
        List<ShopProductResponse> allShopsProducts = companyService.getAllShopsWithProducts();

        List<PaymentReceipt> payments = allShopsProducts.stream()
                .map(shop -> createPayment(shop, response))
                .toList();

        log.info("Generated {} ingredient responses", payments.size());

        return requireNonEmptyCollection(payments);
    }


    @Override
    public List<PaymentReceipt> getPaymentsWithOutMissingIngredients(List<IngredientRequest> requests) {
        List<ShopProductResponse> allShopsProducts = companyService.getAllShopsWithProducts();

        List<PaymentReceipt> ingredientResponses = allShopsProducts.stream()
                .map(shop -> createPayment(shop, requests))
                .filter(payment -> payment.getMissingIngredients().isEmpty())
                .toList();

        log.info("Generated {} ingredient responses", ingredientResponses.size());
        return requireNonEmptyCollection(ingredientResponses);
    }

    @Override
    public List<PaymentReceipt> getTheCheapestPayments(List<IngredientRequest> requests) {

        List<PaymentReceipt> ingredientResponses = getPaymentsWithOutMissingIngredients(requests);

        List<PaymentReceipt> cheapestPayments = ingredientResponses.stream()
                .filter(ir -> ir.getCost() == ingredientResponses.stream()
                        .min(Comparator.comparingDouble(PaymentReceipt::getCost))
                        .orElseThrow(() -> new EmptyResultException("No valid payments found"))
                        .getCost())
                .toList();

        return requireNonEmptyCollection(cheapestPayments);
    }

    //TODO может быть добавить среднюю стоимость для каждой компании


    private PaymentReceipt createPayment(ShopProductResponse shop, List<IngredientRequest> response) {
        log.debug("Creating ingredient response for shop ID: {}", shop.getShop().getId());
        PaymentReceipt paymentReceipt = new PaymentReceipt();
        paymentReceipt.setAddress(shop.getShop().getCity() + " , " + shop.getShop().getAddress());
        paymentReceipt.setCompanyName(shop.getShop().getCompanyName());

        response.forEach(item -> processIngredient(item, shop, paymentReceipt));

        return paymentReceipt;
    }

    private void processIngredient(IngredientRequest item, ShopProductResponse shop, PaymentReceipt paymentReceipt) {
        boolean found = shop.getProducts().stream()
                .filter(product -> product.getName().equals(item.getName()) && product.getCount() >= item.getCount())
                .peek(product -> {
                    log.debug("Found matching product: {} with sufficient count: {}", product.getName(), product.getCount());
                    IngredientResponse ingredient = createIngredientResponse(item, product);
                    paymentReceipt.addIngredient(ingredient);
                    paymentReceipt.setCost(product.getPrice(), item.getCount());
                })
                .findFirst()
                .isPresent();

        if (!found) {
            log.warn("Missing ingredient: {}", item.getName());
            paymentReceipt.addMissingIngredient(item.getName());
        }
    }

    private IngredientResponse createIngredientResponse(IngredientRequest item, ProductResponse product) {
        return new IngredientResponse(item.getName(),
                        item.getCount(),
                        product.getFirm(),
                        product.getPrice() * item.getCount());
    }

}

