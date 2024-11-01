package org.example.foodru_microservice.configuration.kafka;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.properties.kafka")
public class KafkaTopicConfig {

    private String bootstrapAddress;
    private String topicEdadilToFoodRu;
    private String topicFoodRuToEdadil;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicEdadilToFoodRu() {
        return new NewTopic(topicFoodRuToEdadil, 1, (short) 1);
    }

    @Bean
    public NewTopic topicFoodRuToEdadil() {
        return new NewTopic(topicFoodRuToEdadil, 1, (short) 1);
    }
}