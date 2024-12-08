package org.example.foodru_microservice.service.kafka;

import org.example.foodru_microservice.configuration.kafka.KafkaPropertiesConfig;
import org.example.foodru_microservice.service.kafka.dto.ListIngredientDto;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerImplTest {

    KafkaPropertiesConfig config = mock(KafkaPropertiesConfig.class);
    KafkaTemplate<String, ListIngredientDto> kafkaTemplate = mock(KafkaTemplate.class);
    KafkaProducerImpl kafkaProducer = new KafkaProducerImpl(config, kafkaTemplate);

    @Test
    void sendMessage() {
        ListIngredientDto listIngredientDto = new ListIngredientDto();

        when(config.getTopicFoodRuToEdadil()).thenReturn("test-topic");

        kafkaProducer.sendMessage(listIngredientDto);

        verify(kafkaTemplate, times(1)).send("test-topic", listIngredientDto);
        verify(config, times(1)).getTopicFoodRuToEdadil();
    }
}