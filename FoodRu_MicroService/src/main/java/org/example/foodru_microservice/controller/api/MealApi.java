package org.example.foodru_microservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.handler.ApiError;
import org.example.foodru_microservice.model.consts.endpoints.MealEndPoints;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MealApi {

    @Operation(summary = "Get all meals", description = "Retrieve a list of all available meals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of meals retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(MealEndPoints.MEALS)
    List<MealDto> getMeals();

    @Operation(summary = "Get meal by ID", description = "Retrieve a meal by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(MealEndPoints.MEAL)
    MealDto getMealById(@PathVariable Long id);

    @Operation(summary = "Get meal ingredients", description = "Retrieve ingredients of a meal by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal ingredients retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealWithIngredientDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(MealEndPoints.MEAL_INGREDIENTS)
    MealWithIngredientDto getMealIngredients(@PathVariable Long id);

    @Operation(summary = "Get cheapest meal ingredients", description = "Retrieve the cheapest ingredients of a meal by its ID", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cheapest meal ingredients retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentReceiptResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping(MealEndPoints.MEAL_CHEAPEST)
    PaymentReceiptResponse getCheapestMealIngredients(@PathVariable Long id);
}
