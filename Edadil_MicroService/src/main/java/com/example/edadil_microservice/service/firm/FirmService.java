package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.controller.dto.FirmDto;

import java.util.List;

public interface FirmService {

    List<FirmDto> findAllFirms();

    FirmDto findFirmById(Long firmId);

}
