package org.example.foodru_microservice.configuration.kafka;


import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.foodru_microservice.service.kafka.dto.PaymentReceiptResponse;
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
    public ConsumerFactory<String, PaymentReceiptResponse> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        JsonDeserializer<PaymentReceiptResponse> deserializer = new JsonDeserializer<>(PaymentReceiptResponse.class);
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentReceiptResponse>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PaymentReceiptResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}