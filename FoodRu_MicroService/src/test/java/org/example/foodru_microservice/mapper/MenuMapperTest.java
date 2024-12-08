package org.example.foodru_microservice.mapper;

import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.model.entity.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuMapperTest {

    @InjectMocks
    private MenuMapper menuMapper;

    @Test
    void toDto_shouldMapMenuToMenuDto() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Menu");

        MenuDto menuDto = menuMapper.toDto(menu);

        assertNotNull(menuDto);
        assertEquals(1L, menuDto.getId());
        assertEquals("Test Menu", menuDto.getName());
    }
}