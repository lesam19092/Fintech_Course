package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.model.entity.Shop;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ShopMapperTest {

    private ShopMapper shopMapper = new ShopMapper();

    @Test
    void toDto_ShouldConvertShopToShopDto() {
        Company company = new Company();
        company.setCompanyName("Test Company");

        Shop shop = new Shop();
        shop.setId(1L);
        shop.setNameOfCompany(company);
        shop.setCity("Test City");
        shop.setAddress("Test Address");

        ShopDto shopDto = shopMapper.toDto(shop);

        assertAll(
                "Проверка конвертации Shop в ShopDto",
                () -> assertThat(shopDto).isNotNull(),
                () -> assertThat(shopDto.getId()).isEqualTo(1L),
                () -> assertThat(shopDto.getCompanyName()).isEqualTo("Test Company"),
                () -> assertThat(shopDto.getCity()).isEqualTo("Test City"),
                () -> assertThat(shopDto.getAddress()).isEqualTo("Test Address")
        );
    }

    @Test
    void toDtoList_ShouldConvertListOfShopsToListOfShopDtos() {
        Company company1 = new Company();
        company1.setCompanyName("Company 1");

        Shop shop1 = new Shop();
        shop1.setId(1L);
        shop1.setNameOfCompany(company1);
        shop1.setCity("City 1");
        shop1.setAddress("Address 1");

        Company company2 = new Company();
        company2.setCompanyName("Company 2");

        Shop shop2 = new Shop();
        shop2.setId(2L);
        shop2.setNameOfCompany(company2);
        shop2.setCity("City 2");
        shop2.setAddress("Address 2");

        List<Shop> shops = Arrays.asList(shop1, shop2);

        List<ShopDto> shopDtos = shopMapper.toDtoList(shops);

        assertAll(
                "Проверка конвертации списка Shop в список ShopDto",
                () -> assertThat(shopDtos).isNotNull(),
                () -> assertThat(shopDtos).hasSize(2),
                () -> assertThat(shopDtos.get(0).getId()).isEqualTo(1L),
                () -> assertThat(shopDtos.get(1).getId()).isEqualTo(2L)
        );
    }

}
