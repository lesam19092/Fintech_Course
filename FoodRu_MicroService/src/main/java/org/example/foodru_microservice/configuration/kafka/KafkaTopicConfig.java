package org.example.foodru_microservice.configuration.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaPropertiesConfig config;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicFoodRuToEdadil() {
        return new NewTopic(config.getTopicFoodRuToEdadil(), 1, (short) 1);
    }
}