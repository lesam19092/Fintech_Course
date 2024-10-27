package org.example.foodru_microservice;

import org.springframework.boot.SpringApplication;

public class TestFoodRuMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(FoodRuMicroServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
