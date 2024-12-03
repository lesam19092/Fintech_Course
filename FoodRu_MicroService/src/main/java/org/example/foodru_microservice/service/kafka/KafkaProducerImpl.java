package org.example.foodru_microservice.service.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.foodru_microservice.configuration.kafka.KafkaPropertiesConfig;
import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Data
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaPropertiesConfig config;
    private final KafkaTemplate<String, ListIngredientDto> kafkaTemplate;

    @Override
    public void sendMessage(ListIngredientDto listIngredientDto) {
        kafkaTemplate.send(config.getTopicFoodRuToEdadil(), listIngredientDto);
        log.info("Kafka Producer. Sent message: {}", listIngredientDto.toString());
    }
}
