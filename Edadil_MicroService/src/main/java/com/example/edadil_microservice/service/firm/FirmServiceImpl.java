package com.example.edadil_microservice.service.firm;

import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.mapper.FirmMapper;
import com.example.edadil_microservice.model.entity.Firm;
import com.example.edadil_microservice.repository.FirmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirmServiceImpl implements FirmService {


    private final FirmRepository firmRepository;
    private final FirmMapper firmMapper;

    @Override
    public List<FirmDto> findAllFirms() {
        log.info("Fetching all firms");
        List<Firm> firms = firmRepository.findAll();
        requireNonEmptyCollection(firms);
        return firmMapper.toDtoList(firms);
    }

    @Override
    public FirmDto findFirmById(Long firmId) {
        log.info("Fetching firm with ID: {}", firmId);
        return firmMapper.toDto(getFirmById(firmId));
    }

    private Firm getFirmById(Long firmId) {
        return firmRepository.findFirmById(firmId)
                .orElseThrow(() -> new EntityNotFoundException("Firm not found with ID: " + firmId));
    }
}
