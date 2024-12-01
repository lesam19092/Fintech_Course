package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public CompanyDto toDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .build();
    }

    public List<CompanyDto> toDtoList(List<Company> companies) {
        return companies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
