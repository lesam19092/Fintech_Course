package org.example.foodru_microservice.model.consts.endpoints;

public interface UserEndPoints {

    String ADD_MEAL = "/meals/{mealId}/ingredients/add";
    String ADD_MEAL_TO_MENU = "/meals/{mealId}/ingredients/add/{menuName}";
    String CREATE_MENU = "/menu/create/{menuName}";
    String USER_MEALS = "/get-meals";
}
