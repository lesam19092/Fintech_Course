package org.example.foodru_microservice.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.foodru_microservice.handler.ApiError;
import org.example.foodru_microservice.model.consts.endpoints.PurchaseEndPoints;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.Principal;

public interface PurchaseApi {

    @Operation(summary = "Buy cheapest meal ingredients", description = "Purchase the cheapest meal ingredients by meal ID for the authenticated user", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment was successful",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping(PurchaseEndPoints.BUY_MEAL)
    String buyCheapestMealIngredients(@PathVariable Long id, Principal principal) throws IOException;
}
