package com.example.edadil_microservice;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final DockerImageName IMAGE = DockerImageName.parse("postgres:14-alpine");
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(IMAGE)
            .withDatabaseName("test_db")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("test_schema.sql");

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        CONTAINER.start();
        TestPropertyValues.of(
                "spring.datasource.url=" + CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + CONTAINER.getUsername(),
                "spring.datasource.password=" + CONTAINER.getPassword(),
                "spring.datasource.driver-class-name=org.postgresql.Driver",
                "spring.liquibase.enabled=true"
        ).applyTo(context.getEnvironment());
    }
}

