package com.example.edadil_microservice.controller.api;

import com.example.edadil_microservice.controller.dto.ProductDto;
import com.example.edadil_microservice.handler.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ProductsEndpoint.GET_FIRM_PRODUCTS;
import static com.example.edadil_microservice.model.consts.endpoints.ProductsEndpoint.GET_FIRM_PRODUCT_BY_ID;

public interface ProductsApi {


    @Operation(summary = "Get all firm products", description = "Retrieve a list of all products for a specific firm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_FIRM_PRODUCTS)
    List<ProductDto> getFirmProducts(@PathVariable Long firmId);

    @Operation(summary = "Get firm product by ID", description = "Retrieve a specific product by its ID for a specific firm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_FIRM_PRODUCT_BY_ID)
    ProductDto getFirmProductById(@PathVariable Long firmId, @PathVariable Long productId);

}
