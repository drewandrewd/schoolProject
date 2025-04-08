package com.example.schoolproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса {@link NotificationService} для отправки уведомлений по электронной почте.
 * <p>
 * Использует {@link JavaMailSender} для формирования и отправки писем пользователям
 * при изменении статуса задачи.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendStatusChangeEmail(Long taskId, String status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("andrewid10@gmail.com");
        message.setSubject("Task Status Updated");
        message.setText("Task ID " + taskId + " has changed status to: " + status);
        mailSender.send(message);
    }
}
