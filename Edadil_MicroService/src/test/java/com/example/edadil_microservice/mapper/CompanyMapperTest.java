package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.model.entity.Company;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CompanyMapperTest {

    private CompanyMapper companyMapper = new CompanyMapper();


    @Test
    void toDto() {
        Company company = new Company();
        company.setId(1L);
        company.setCompanyName("Test Company");

        CompanyDto companyDto = companyMapper.toDto(company);

        assertAll(
                "Проверка конвертации Company в CompanyDto",
                () -> assertThat(companyDto).isNotNull(),
                () -> assertThat(companyDto.getId()).isEqualTo(1L),
                () -> assertThat(companyDto.getCompanyName()).isEqualTo("Test Company")
        );
    }

    @Test
    void toDtoList() {
        Company company1 = new Company();
        company1.setId(1L);
        company1.setCompanyName("Company 1");

        Company company2 = new Company();
        company2.setId(2L);
        company2.setCompanyName("Company 2");

        List<Company> companies = Arrays.asList(company1, company2);

        List<CompanyDto> companyDtos = companyMapper.toDtoList(companies);

        assertAll(
                "Проверка конвертации списка Company в список CompanyDto",
                () -> assertThat(companyDtos).isNotNull(),
                () -> assertThat(companyDtos).hasSize(2),
                () -> assertThat(companyDtos.get(0).getId()).isEqualTo(1L),
                () -> assertThat(companyDtos.get(1).getId()).isEqualTo(2L)
        );
    }

    @Test
    void toDtoList_ShouldReturnEmptyListWhenInputIsEmpty() {
        List<Company> companies = List.of();
        List<CompanyDto> companyDtos = companyMapper.toDtoList(companies);

        assertAll(
                "Проверка конвертации пустого списка Company в пустой список CompanyDto",
                () -> assertThat(companyDtos).isNotNull(),
                () -> assertThat(companyDtos).isEmpty()
        );
    }
}
