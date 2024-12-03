package org.example.foodru_microservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.handler.ApiError;
import org.example.foodru_microservice.model.consts.endpoints.UserEndPoints;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

public interface UserApi {


    @Operation(summary = "Add a meal ", description = "Add a meal to a user menu  meal ID and menu name for the authenticated user", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal added to menu successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")

    @PostMapping(UserEndPoints.ADD_MEAL)
    String addMeal(@PathVariable Long mealId, Principal principal);

    @Operation(summary = "Add a meal to menu", description = "Add a meal to a specific menu by meal ID and menu name for the authenticated user", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal added to menu successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")

    @PostMapping(UserEndPoints.ADD_MEAL_TO_MENU)
    String addMealToMenu(@PathVariable Long mealId, @PathVariable String menuName, Principal principal);


    @Operation(summary = "Create a menu", description = "Create a new menu for the authenticated user", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menu created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")

    @PostMapping(UserEndPoints.CREATE_MENU)
    String createMenu(Principal principal, @PathVariable String menuName);


    @Operation(summary = "Get all meals", description = "Retrieve all meals for the authenticated user", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of meals retrieved",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping(UserEndPoints.USER_MEALS)
    List<MealDto> getAllMeals(Principal principal);
}
