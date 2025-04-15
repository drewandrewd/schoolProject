package com.example.schoolproject.kafka;

import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import com.example.schoolproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kafka-консьюмер для обработки сообщений о смене статуса задачи.
 * <p>
 * Слушает указанный топик Kafka и передаёт полученные данные в {@link NotificationService}
 * для отправки уведомлений пользователям.
 * Ожидает сообщения в формате {@link TaskStatusUpdateDTO}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTaskConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${task.kafka.topic.status-updated}",
            groupId = "${task.kafka.consumer.group-id}",
            containerFactory = "kafkaBatchListenerContainerFactory"
    )
    public void listen(
            @Payload List<TaskStatusUpdateDTO> messages,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            Acknowledgment ack
    ) {
        log.info("KafkaConsumer: получена пачка из {} сообщений из топика {}", messages.size(), topic);

        try {
            messages.forEach(dto -> {
                log.debug("Обработка задачи ID={}, новый статус={}", dto.getTaskId(), dto.getNewStatus());
                notificationService.sendStatusChangeEmail(dto.getTaskId(), dto.getNewStatus());
            });
            ack.acknowledge();
            log.info("KafkaConsumer: пачка сообщений успешно обработана и подтверждена.");

        } catch (Exception e) {
            log.error("Ошибка при обработке пачки сообщений: {}", e.getMessage(), e);
        }
    }
}
