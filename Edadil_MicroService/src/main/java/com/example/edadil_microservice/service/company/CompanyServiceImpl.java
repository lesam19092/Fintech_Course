package com.example.edadil_microservice.service.company;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.handler.exception.EntityNotFoundException;
import com.example.edadil_microservice.handler.exception.NoCompaniesSellingFirmProductsException;
import com.example.edadil_microservice.mapper.CompanyMapper;
import com.example.edadil_microservice.model.entity.Company;
import com.example.edadil_microservice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.edadil_microservice.utils.EntityUtils.requireNonEmptyCollection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> findAllCompanies() {
        log.info("Fetching all companies");
        List<Company> companies = companyRepository.findAll();
        requireNonEmptyCollection(companies);
        return companyMapper.toDtoList(companies);
    }

    @Override
    public CompanyDto findCompanyById(Long companyId) {
        log.info("Fetching company with ID: {}", companyId);
        Company company = getCompanyById(companyId);
        return companyMapper.toDto(company);
    }

    @Override
    public List<CompanyDto> findCompaniesSellingFirmProducts(Long firmId) {
        log.info("Fetching companies selling products for firm with ID: {}", firmId);
        List<Company> companies = getCompaniesByFirmId(firmId);
        return companyMapper.toDtoList(companies);
    }

    @Override
    public CompanyDto findCompanySellingFirmProductsById(Long firmId, Long companyId) {
        log.info("Fetching company with ID: {} selling products for firm with ID: {}", companyId, firmId);
        return companyMapper.toDto(getCompanyByFirmAndCompanyId(firmId, companyId));
    }

    private Company getCompanyByFirmAndCompanyId(Long firmId, Long companyId) {
        return companyRepository.findByFirmIdAndCompanyId(firmId, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with firm ID: " + firmId + " and company ID: " + companyId));
    }

    private Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + companyId));
    }

    private List<Company> getCompaniesByFirmId(Long firmId) {
        List<Company> companies = companyRepository.findCompaniesByFirmId(firmId);
        if (companies.isEmpty()) {
            throw new NoCompaniesSellingFirmProductsException("No companies found selling products for firm ID: " + firmId);
        }
        return companies;
    }


}
