package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.BaseTestContainer;
import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.mapper.FirmMapper;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.repository.FirmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FirmServiceImplTest extends BaseTestContainer {

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private FirmService firmService;

    @Autowired
    private FirmMapper firmMapper;

    @Before

    public void initializeDatabase() {
        Firm firm = new Firm();
        firm.setId(1L);
        firm.setFirmName("Test Firm");
        firmRepository.save(firm);
    }

    @AfterAll
    public void cleanUp() {
        firmRepository.deleteAll();
    }

    @Test
    public void findAllFirms_ShouldReturnListOfFirms() {
        List<FirmDto> firmDtos = firmService.findAllFirms();
        assertThat(firmDtos).isNotEmpty();
        assertThat(firmDtos.get(0).getFirmName()).isEqualTo("Test Firm");
    }

    @Test
    public void findFirmById_ShouldReturnFirmDto_WhenFirmExists() {
        Optional<Firm> firm = firmRepository.findById(1L);
        FirmDto firmDto = firmMapper.toDto(firm.get());

        assertThat(firmDto).isNotNull();
        assertThat(firmDto.getFirmName()).isEqualTo("Test Firm");
    }

    @Test
    public void findFirmById_ShouldThrowEntityNotFoundException_WhenFirmDoesNotExist() {
        Long nonExistentId = 999L;
        assertThrows(EntityNotFoundException.class, () -> firmService.findFirmById(nonExistentId));
    }
}
