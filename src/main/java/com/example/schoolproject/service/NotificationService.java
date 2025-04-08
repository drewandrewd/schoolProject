package com.example.schoolproject.service;

/**
 * Сервис для отправки уведомлений пользователям.
 * <p>
 * Используется для оповещения о смене статуса задачи, полученного через Kafka..
 */
public interface NotificationService {
    void sendStatusChangeEmail(Long taskId, String status);
}
