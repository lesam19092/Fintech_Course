package org.example.foodru_microservice.controller;


import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.service.kafka.dto.IngredientDto;
import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;
import org.example.foodru_microservice.controller.dto.MealDto;
import org.example.foodru_microservice.controller.dto.MealWithIngredientDto;
import org.example.foodru_microservice.service.mail.EmailService;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.pdf.PdfService;
import org.example.foodru_microservice.service.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-v1")
public class MealController {


    private final MealService mealService;

    private final EmailService emailService;

    private final PdfService pdfService;

    private final KafkaProducer kafkaProducer;


    @GetMapping("/meals")
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/meals/{id}")
    public MealDto getMealById(@PathVariable Long id) {
        return mealService.getMealDtoById(id);
    }

    @GetMapping("/meals/{id}/ingredients")
    public MealWithIngredientDto getMealIngredients(@PathVariable Long id) {
        return mealService.getMealsIngredients(id);
    }



    @GetMapping("/meals/{id}/ingredients/cheapest")
    public MealWithIngredientDto getCheapestMealIngredients(@PathVariable Long id) {
        mealService.getCheapestMealsIngredients(id);
        return null;
    }


    @GetMapping("/mail/{email}")
    public String getMail(@PathVariable String email) throws IOException {

        ListIngredientDto listIngredientDto = new ListIngredientDto();

        IngredientDto ingredientDto = IngredientDto.builder().name("Мясо").measure(1.0).build();
        IngredientDto ingredientDto1 = IngredientDto.builder().name("гречка").measure(20.0).build();
        IngredientDto ingredientDto2 = IngredientDto.builder().name("соль").measure(0.5).build();

        listIngredientDto.setIngredientDtoList(List.of(ingredientDto, ingredientDto1, ingredientDto2));

        kafkaProducer.sendMessage(listIngredientDto);
        // pdfService.generateAndSendPdfReport(new ArrayList<>());
        // emailService.sendEmailWithAttachment("danigpro1337@gmail.com", "C:\\Users\\danil\\Desktop\\2305_L1_Палев_Гловацкий.pdf");
        return "mail";

        //pdfService.savePdf(new ArrayList<>());*/


    }
}
