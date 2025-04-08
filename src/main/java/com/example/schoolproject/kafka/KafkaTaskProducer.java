package com.example.schoolproject.kafka;

import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka-продюсер для отправки сообщений о смене статуса задачи.
 * <p>
 * Использует {@link KafkaTemplate} для публикации объектов {@link TaskStatusUpdateDTO}
 * в указанный топик Kafka.
 * Применяется в сервисе задач при обновлении статуса.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTaskProducer {

    private final KafkaTemplate<String, TaskStatusUpdateDTO> kafkaTemplate;

    public void sendTo(String topic, TaskStatusUpdateDTO dto) {
        try {
            kafkaTemplate.send(topic, dto).get();
            kafkaTemplate.flush();
            log.info("KafkaProducer: message sent to topic [{}]: {}", topic, dto);
        } catch (Exception ex) {
            log.error("KafkaProducer: error while sending message - {}", ex.getMessage(), ex);
        }
    }
}
