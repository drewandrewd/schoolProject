package com.example.schoolproject.config;

import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka для продюсера и консьюмера, работающих с {@link TaskStatusUpdateDTO}.
 * <p>
 * Содержит настройки сериализации и десериализации, шаблон KafkaTemplate и
 * фабрику контейнеров для обработки сообщений.
 * <p>
 * Используется в связке с {@code KafkaTaskProducer} и {@code KafkaTaskConsumer}.
 */
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, TaskStatusUpdateDTO> consumerFactory() {
        JsonDeserializer<TaskStatusUpdateDTO> deserializer = new JsonDeserializer<>(TaskStatusUpdateDTO.class);
        deserializer.addTrustedPackages("*");
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "task-status-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public KafkaTemplate<String, TaskStatusUpdateDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, TaskStatusUpdateDTO> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskStatusUpdateDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskStatusUpdateDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
