package org.example.foodru_microservice.service.purchase;

import org.example.foodru_microservice.model.entity.User;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.pdf.PdfService;
import org.example.foodru_microservice.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private MealService mealService;

    @Mock
    private UserService userService;

    @Mock
    private PdfService pdfService;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Test
    void buyCheapestMealsIngredients() throws IOException {
        Long mealId = 1L;
        String userName = "testUser";
        PaymentReceiptResponse response = new PaymentReceiptResponse();
        User user = new User();

        when(mealService.getCachedResponse(mealId)).thenReturn(response);
        when(userService.getUserByName(userName)).thenReturn(user);

        purchaseService.buyCheapestMealsIngredients(mealId, userName);

        verify(mealService).getCachedResponse(mealId);
        verify(userService).getUserByName(userName);
        verify(pdfService).generateAndSendPdfReport(response, user);
    }
}
