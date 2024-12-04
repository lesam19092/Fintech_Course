package org.example.foodru_microservice.configuration.kafka;

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
