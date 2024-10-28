package org.example.foodru_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.foodru_microservice.repository")

public class FoodRuMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodRuMicroServiceApplication.class, args);
    }

}
