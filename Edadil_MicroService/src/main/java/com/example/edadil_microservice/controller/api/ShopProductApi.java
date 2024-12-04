package com.example.edadil_microservice.controller.api;

import com.example.edadil_microservice.controller.dto.ShopProductDto;
import com.example.edadil_microservice.handler.ApiError;
import com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.edadil_microservice.model.consts.endpoints.ShopProductEndpoints.GET_COMPANY_SHOP_PRODUCTS;

public interface ShopProductApi {

    @Operation(summary = "Get company shops", description = "Retrieve a list of all shops for a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopProductDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(GET_COMPANY_SHOP_PRODUCTS)
    ShopProductDto getCompanyShopProducts(@PathVariable Long companyId, @PathVariable String city, @PathVariable Long shopId);

    @Operation(summary = "Get shops with product", description = "Retrieve a list of all shops that sell a specific product from a specific firm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shops retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopProductDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(ShopProductEndpoints.GET_SHOPS_WITH_PRODUCT)
    List<ShopProductDto> getShopsWithProduct(@PathVariable Long firmId, @PathVariable Long productId);

    @Operation(summary = "Find firm products in shop", description = "Retrieve products from a specific firm in a specific shop of a specific company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShopProductDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(ShopProductEndpoints.GET_FIRM_PRODUCTS_IN_SHOP)
    ShopProductDto findFirmProductsInShop(@PathVariable Long firmId, @PathVariable Long companyId, @PathVariable Long shopId);

}
