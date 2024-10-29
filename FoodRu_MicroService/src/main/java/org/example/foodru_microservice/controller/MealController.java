package org.example.foodru_microservice.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.foodru_microservice.model.dto.MealDto;
import org.example.foodru_microservice.model.dto.MealWithIngredientDto;
import org.example.foodru_microservice.service.meal.MealService;
import org.example.foodru_microservice.service.mail.EmailService;
import org.example.foodru_microservice.service.pdf.PdfService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MealController {


    private final MealService mealService;

    private final EmailService emailService;

    private final PdfService pdfService;


    @GetMapping("/meals")
    public List<MealDto> getMeals() {
        return mealService.getAllMeals();
    }

    @GetMapping("/meals/{id}")
    public MealDto getMealById(@PathVariable Integer id) {
        return mealService.getMealById(id);
    }

    @GetMapping("/meals/{id}/ingredients")
    public MealWithIngredientDto getMealIngredients(@PathVariable Integer id) {
        return mealService.getMealsIngredients(id);
    }

    @GetMapping("/mail")
    public String getMail() throws MessagingException, IOException {

       /* emailService.sendEmailWithAttachment("danigpro1337@gmail.com", "", "Test", "C:\\Users\\danil\\Desktop\\2305_L1_Палев_Гловацкий.pdf");
        return "mail";*/

        pdfService.savePdf(new ArrayList<>());

        return "mail";
    }
}
