package com.example.edadil_microservice.mapper;

import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.model.entity.Firm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FirmMapper {

    public FirmDto toDto(Firm firm) {

        return FirmDto.builder()
                .id(firm.getId())
                .firmName(firm.getFirmName())
                .build();
    }

    public List<FirmDto> toDtoList(List<Firm> firms) {
        return firms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
