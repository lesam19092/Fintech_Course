package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.BaseTestContainer;
import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertThrows;


@SpringBootTest
@RunWith(SpringRunner.class)
public class FirmServiceImplTest extends BaseTestContainer {


    @Autowired
    private FirmService firmService;


    @Test
    public void findAllFirms_ShouldReturnListOfFirms() {
        List<FirmDto> firmDtos = firmService.findAllFirms();
        assertThat(firmDtos).isNotEmpty();
        assertThat(firmDtos.get(0).getFirmName()).isEqualTo("Простоквашино");
    }

    @Test
    public void findFirmById_ShouldReturnFirmDto_WhenFirmExists() {
        FirmDto firmDto = firmService.findFirmById(1L);

        assertThat(firmDto).isNotNull();
        assertThat(firmDto.getFirmName()).isEqualTo("Простоквашино");
    }

    @Test
    public void findFirmById_ShouldThrowEntityNotFoundException_WhenFirmDoesNotExist() {
        Long nonExistentId = 100L;
        assertThrows(EntityNotFoundException.class, () -> firmService.findFirmById(nonExistentId));
    }

}
