package com.example.edadil_microservice;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("postgres:17-alpine");
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE)
            .withDatabaseName("test_db")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("test_schema.sql");

    private static final DockerImageName KAFKA_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:7.6.1");
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(KAFKA_IMAGE)
            .withExposedPorts(9093);

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        POSTGRES_CONTAINER.start();
        KAFKA_CONTAINER.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + POSTGRES_CONTAINER.getJdbcUrl(),
                "spring.datasource.username=" + POSTGRES_CONTAINER.getUsername(),
                "spring.datasource.password=" + POSTGRES_CONTAINER.getPassword(),
                "spring.datasource.driver-class-name=org.postgresql.Driver",
                "spring.liquibase.enabled=true"
        ).applyTo(context.getEnvironment());

        TestPropertyValues.of(
                "spring.properties.kafka.bootstrapAddress=" + KAFKA_CONTAINER.getBootstrapServers(),
                "spring.kafka.consumer.group-id=testEdadilGroup",
                "spring.kafka.consumer.auto-offset-reset=earliest",
                "spring.kafka.listener.type=batch"
        ).applyTo(context.getEnvironment());
    }
}

