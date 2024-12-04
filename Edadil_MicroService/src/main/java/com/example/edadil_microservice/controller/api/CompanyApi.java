package com.example.edadil_microservice.controller.api;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.handler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.СompanyEndpoints.*;

public interface CompanyApi {


    @Operation(summary = "Get all companies", description = "Retrieve a list of all available companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_ALL_COMPANIES)
    List<CompanyDto> getAllCompany();

    @Operation(summary = "Get company by ID", description = "Retrieve a company by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANY_BY_ID)
    CompanyDto getCompanyById(@PathVariable Long companyId);

    @Operation(summary = "Get companies having firm products", description = "Retrieve companies that have firm products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANIES_HAVING_FIRM_PRODUCTS)
    List<CompanyDto> getCompaniesHavingFirmProducts(@PathVariable Long firmId);

    @Operation(summary = "Get company having firm products by ID", description = "Retrieve a company that has firm products by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompanyDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANIES_HAVING_FIRM_PRODUCTS_BY_ID)
    CompanyDto getCompaniesHavingFirmProductsById(@PathVariable Long firmId, @PathVariable Long companyId);

}
