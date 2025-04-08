package com.example.schoolproject.kafka;

import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import com.example.schoolproject.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(TaskStatusUpdateDTO dto) {
        log.info("KafkaConsumer: received message from topic — {}", dto);
        notificationService.sendStatusChangeEmail(dto.getTaskId(), dto.getNewStatus());
    }
}
