package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.service.firm.FirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FirmController {

    private final FirmService firmService;

    @GetMapping("/firms")
    public List<FirmDto> getAllFirms() {
        return firmService.findAllFirms();
    }

    @GetMapping("/firms/{firmId}")
    public FirmDto getFirmById(@PathVariable Integer firmId) {
        return firmService.findFirmById(firmId);
    }


}

