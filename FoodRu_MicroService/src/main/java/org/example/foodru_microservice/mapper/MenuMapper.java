package org.example.foodru_microservice.mapper;


import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .build();
    }

}
