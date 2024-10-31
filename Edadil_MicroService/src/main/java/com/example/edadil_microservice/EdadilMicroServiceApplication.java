package com.example.edadil_microservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.edadil_microservice.repository")
@Slf4j
public class EdadilMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdadilMicroServiceApplication.class, args);
    }
}

//todo connect hikaricp
