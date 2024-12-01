package com.example.edadil_microservice.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDto {
    private Integer id;
    private String companyName;
}