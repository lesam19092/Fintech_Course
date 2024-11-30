package org.example.foodru_microservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.consts.endpoints.MenuEndPoints;
import org.example.foodru_microservice.service.menu.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MenuController {

    private final MenuService menuService;


    @GetMapping(MenuEndPoints.MENUS)
    public List<MenuDto> getMenus(Principal principal) {
        return menuService.getMenusByUsername(principal.getName());
    }

    @GetMapping(MenuEndPoints.MENU)
    public List<MealDto> getMealsByMenuId(@PathVariable Long id) {
        return menuService.getMealsByMenuId(id);
    }


}
