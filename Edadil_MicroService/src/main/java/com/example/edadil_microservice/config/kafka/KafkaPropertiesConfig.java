package com.example.edadil_microservice.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.properties.kafka")
public class KafkaPropertiesConfig {
    private String bootstrapAddress;
    private String topicFoodRuToEdadil;
    private String topicEdadilToFoodRu;
    private String groupId;
}
