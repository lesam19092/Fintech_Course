package org.example.foodru_microservice.service.purchase;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.pdf.PdfService;
import org.example.foodru_microservice.service.user.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {


    private final MealService mealService;
    private final UserService userService;
    private final PdfService pdfService;

    @Override
    public void buyCheapestMealsIngredients(Long mealId, String userName) throws IOException {
        PaymentReceiptResponse response = mealService.getCachedResponse(mealId);
        User user = userService.getUserByName(userName);
        pdfService.generateAndSendPdfReport(response, user);
    }
}
