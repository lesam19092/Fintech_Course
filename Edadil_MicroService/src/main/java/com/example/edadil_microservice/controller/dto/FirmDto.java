package com.example.edadil_microservice.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FirmDto {

    private Long id;
    private String firmName;

}

