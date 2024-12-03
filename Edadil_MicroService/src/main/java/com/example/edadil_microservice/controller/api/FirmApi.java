package com.example.edadil_microservice.controller.api;

import com.example.edadil_microservice.controller.dto.CompanyDto;
import com.example.edadil_microservice.controller.dto.FirmDto;
import com.example.edadil_microservice.handler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_ALL_FIRMS;
import static com.example.edadil_microservice.model.consts.endpoints.FirmEndpoints.GET_FIRM_BY_ID;

public interface FirmApi {


    @Operation(summary = "Get all firms", description = "Retrieve a list of all firms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Firms retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FirmDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_ALL_FIRMS)
    List<FirmDto> getAllFirms();

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
    @GetMapping(GET_FIRM_BY_ID)
    FirmDto getFirmById(@PathVariable Integer firmId);
}
