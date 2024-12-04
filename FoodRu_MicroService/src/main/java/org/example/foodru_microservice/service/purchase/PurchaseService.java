package org.example.foodru_microservice.service.purchase;

import java.io.IOException;

public interface PurchaseService {

    void buyCheapestMealsIngredients(Long mealId, String userName) throws IOException;
}
