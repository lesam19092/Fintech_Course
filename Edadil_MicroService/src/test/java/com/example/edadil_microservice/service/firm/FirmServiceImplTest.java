package com.example.edadil_microservice.service.firm;


import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.repository.FirmRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertThrows;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FirmServiceImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("test_db")
                    .withUsername("postgres")
                    .withPassword("postgres");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    private FirmServiceImpl firmService;

    @Autowired
    private FirmRepository firmRepository;


    @Before
    public void setUp() {
        Firm firm = new Firm();
        firm.setId(1L);
        firm.setFirmName("Test Firm");
        firmRepository.save(firm);
    }


    @Test
    public void findAllFirms_ShouldReturnListOfFirms() {
        List<FirmDto> firmDtos = firmService.findAllFirms();

        for (FirmDto firmDto : firmDtos) {
            System.out.println(firmDto.getFirmName() + firmDto.getId());
        }


        assertThat(firmDtos).isNotEmpty();
        assertThat(firmDtos.get(0).getFirmName()).isEqualTo("Test Firm");
    }

    @Test
    public void findFirmById_ShouldReturnFirmDto_WhenFirmExists() {
        FirmDto firmDto = firmService.findFirmById(1L);

        assertThat(firmDto).isNotNull();
        assertThat(firmDto.getFirmName()).isEqualTo("Test Firm");
    }

    @Test
    public void findFirmById_ShouldThrowEntityNotFoundException_WhenFirmDoesNotExist() {
        Long nonExistentId = 999L;

        assertThrows(EntityNotFoundException.class, () -> firmService.findFirmById(nonExistentId));
    }
}
