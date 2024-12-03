package org.example.foodru_microservice.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MenuDto;
import org.example.foodru_microservice.handler.ApiError;
import org.example.foodru_microservice.model.consts.endpoints.MenuEndPoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

public interface MenuApi {


    @Operation(summary = "Get all menus", description = "Retrieve a list of all available menus for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of menus retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MenuDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(MenuEndPoints.MENUS)
    List<MenuDto> getMenus(Principal principal);

    @Operation(summary = "Get meals by menu ID", description = "Retrieve a list of meals by menu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of meals retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MealDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Menu not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping(MenuEndPoints.MENU)
    List<MealDto> getMealsByMenuId(@PathVariable Long id);
}
