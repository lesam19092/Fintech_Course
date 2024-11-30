package org.example.foodru_microservice.model.consts.endpoints;

public interface UserEndPoints {

    String ADD_MEAL = "/meals/{id}/ingredients/add";
    String CREATE_MENU = "/menu/create/{menuName}";
}
