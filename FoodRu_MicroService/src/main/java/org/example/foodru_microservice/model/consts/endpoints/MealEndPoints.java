package org.example.foodru_microservice.model.consts.endpoints;

public interface MealEndPoints {

    String MEALS = "/meals";

    String MEAL = "/meals/{id}";

    String MEAL_INGREDIENTS = "/meals/{id}/ingredients";

    String MEAL_CHEAPEST = "/meals/{id}/ingredients/cheapest";

}
