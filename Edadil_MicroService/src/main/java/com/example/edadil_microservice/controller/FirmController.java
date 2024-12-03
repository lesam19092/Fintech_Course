package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.api.FirmApi;
import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.service.firm.FirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_ALL_FIRMS;
import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_FIRM_BY_ID;

@RestController
@RequiredArgsConstructor
public class FirmController implements FirmApi {

    private final FirmService firmService;


    @GetMapping(GET_ALL_FIRMS)
    public List<FirmDto> getAllFirms() {
        return firmService.findAllFirms();
    }

    @GetMapping(GET_FIRM_BY_ID)
    public FirmDto getFirmById(@PathVariable Integer firmId) {
        return firmService.findFirmById(firmId);
    }


}

