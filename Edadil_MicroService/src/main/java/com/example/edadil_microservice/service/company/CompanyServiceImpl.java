package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.mapper.CompanyMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;
import static com.example.edadil_microservice.utils.EntityUtils.requirePresentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> findAllCompanies() {
        log.info("Fetching all companies");
        List<Company> companies = requireNonEmptyCollection(companyRepository.findAll());
        return companyMapper.toDtoList(companies);
    }

    @Override
    public CompanyDto findCompanyById(Integer companyId) {
        log.info("Fetching company with ID: {}", companyId);
        Company company = requirePresentEntity(companyRepository.findById(companyId));
        return companyMapper.toDto(company);
    }

    @Override
    public List<CompanyDto> findCompaniesSellingFirmProducts(Integer firmId) {
        log.info("Fetching companies selling products for firm with ID: {}", firmId);
        List<Company> companies = requireNonEmptyCollection(companyRepository.findCompaniesByFirmId(firmId));
        return companyMapper.toDtoList(companies);
    }

    @Override
    public CompanyDto findCompanySellingFirmProductsById(Integer firmId, Integer companyId) {
        log.info("Fetching company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        Optional<Company> company = companyRepository.findByFirmIdAndCompanyId(firmId, companyId);
        return companyMapper.toDto(requirePresentEntity(company));
    }


}