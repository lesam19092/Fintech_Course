package com.example.edadil_microservice.controller.api;

import com.example.edadil_microservice.controller.dto.ShopDto;
import com.example.edadil_microservice.handler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopEndpoints.*;

public interface ShopApi {

    @Operation(summary = "Get company shops", description = "Retrieve a list of all shops for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANY_SHOPS)
    List<ShopDto> getCompanyShops(@PathVariable Long companyId);

    @Operation(summary = "Get company shops by city", description = "Retrieve a list of all shops for a specific company in a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANY_SHOPS_BY_CITY)
    List<ShopDto> getCompanyShopsByCity(@PathVariable Long companyId, @PathVariable String city);

    @Operation(summary = "Get company shop from city by ID", description = "Retrieve a specific shop by its ID for a specific company in a specific city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANY_SHOP_FROM_CITY_BY_ID)
    ShopDto getCompanyShopFromCityById(@PathVariable Long companyId, @PathVariable String city, @PathVariable Long shopId);

    @Operation(summary = "Get shops in company with firm products", description = "Retrieve a list of all shops for a specific company that have products from a specific firm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS)
    List<ShopDto> getShopsInCompanyWithFirmProducts(@PathVariable Long firmId, @PathVariable Long companyId);


    @Operation(summary = "Get shop in company with firm products by ID", description = "Retrieve a specific shop by its ID for a specific company that has products from a specific firm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shop retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_SHOPS_IN_COMPANY_WITH_FIRM_PRODUCTS_BY_ID)
    ShopDto getShopsInCompanyWithFirmProductsById(@PathVariable Long firmId, @PathVariable Long companyId, @PathVariable Long shopId);
}
