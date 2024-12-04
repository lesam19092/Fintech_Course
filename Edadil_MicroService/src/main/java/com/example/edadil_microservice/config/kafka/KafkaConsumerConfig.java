package com.example.edadil_microservice.config.kafka;

import com.example.edadil_microservice.controller.dto.ListIngredientDto;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Data
public class KafkaConsumerConfig {


    private final KafkaPropertiesConfig config;

    @Bean
    public ConsumerFactory<String, ListIngredientDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        JsonDeserializer<ListIngredientDto> deserializer = new JsonDeserializer<>(ListIngredientDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                config.getBootstrapAddress());
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                config.getGroupId());
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                deserializer);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ListIngredientDto>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ListIngredientDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}