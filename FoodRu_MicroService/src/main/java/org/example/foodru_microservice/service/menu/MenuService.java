package org.example.foodru_microservice.service.menu;

import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.entity.User;

import java.util.List;

public interface MenuService {
    boolean createMenu(User user, String menuName);

    List<MenuDto> getMenusByUsername(String username);

    List<MealDto> getMealsByMenuId(Long id);


}
