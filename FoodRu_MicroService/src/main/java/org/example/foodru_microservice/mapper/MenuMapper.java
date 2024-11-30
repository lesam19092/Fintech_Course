package org.example.foodru_microservice.mapper;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.entity.Menu;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuMapper {

    public MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .build();
    }

}
