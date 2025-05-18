package com.demoApp.common_kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Minimal Kafka configuration for development without a real Kafka broker
 */
@Configuration
public class KafkaConfig {

    /**
     * Creates a producer factory with empty configuration
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        // Empty configuration - will not actually connect to Kafka
        Map<String, Object> configProps = new HashMap<>();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates a KafkaTemplate with the mock producer factory
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        KafkaTemplate<String, Object> template = new KafkaTemplate<>(producerFactory());
        return template;
    }
} 