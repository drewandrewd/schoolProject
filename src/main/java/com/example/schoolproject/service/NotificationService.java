package com.example.schoolproject.service;

import com.example.schoolproject.entity.TaskStatus;

/**
 * Сервис для отправки уведомлений пользователям.
 * <p>
 * Используется для оповещения о смене статуса задачи, полученного через Kafka..
 */
public interface NotificationService {
    void sendStatusChangeEmail(Long taskId, TaskStatus status);
}
