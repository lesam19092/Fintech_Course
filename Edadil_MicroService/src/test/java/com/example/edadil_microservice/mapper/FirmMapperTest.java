package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.model.entity.Firm;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FirmMapperTest {

    private FirmMapper firmMapper = new FirmMapper();

    @Test
    void toDto_ShouldConvertFirmToFirmDto() {
        Firm firm = new Firm();
        firm.setId(1L);
        firm.setFirmName("Test Firm");

        FirmDto firmDto = firmMapper.toDto(firm);

        assertAll(
                "Проверка конвертации Firm в FirmDto",
                () -> assertThat(firmDto).isNotNull(),
                () -> assertThat(firmDto.getId()).isEqualTo(1L),
                () -> assertThat(firmDto.getFirmName()).isEqualTo("Test Firm")
        );
    }

    @Test
    void toDtoList_ShouldConvertListOfFirmsToListOfFirmDtos() {
        Firm firm1 = new Firm();
        firm1.setId(1L);
        firm1.setFirmName("Firm 1");

        Firm firm2 = new Firm();
        firm2.setId(2L);
        firm2.setFirmName("Firm 2");

        List<Firm> firms = Arrays.asList(firm1, firm2);

        List<FirmDto> firmDtos = firmMapper.toDtoList(firms);

        assertAll(
                "Проверка конвертации списка Firm в список FirmDto",
                () -> assertThat(firmDtos).isNotNull(),
                () -> assertThat(firmDtos).hasSize(2),
                () -> assertThat(firmDtos.get(0).getId()).isEqualTo(1L),
                () -> assertThat(firmDtos.get(1).getId()).isEqualTo(2L)
        );
    }

    @Test
    void toDtoList_ShouldReturnEmptyListWhenInputIsEmpty() {
        List<Firm> firms = List.of();
        List<FirmDto> firmDtos = firmMapper.toDtoList(firms);
        assertAll(
                "Проверка конвертации пустого списка Firm в пустой список FirmDto",
                () -> assertThat(firmDtos).isNotNull(),
                () -> assertThat(firmDtos).isEmpty()
        );
    }

}
