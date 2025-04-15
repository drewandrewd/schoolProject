package com.example.schoolproject.service;

import com.example.schoolproject.entity.TaskStatus;
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

    @Value("${app.mail.default-recipient}")
    private String defaultRecipient;

    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendStatusChangeEmail(Long taskId, TaskStatus status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(defaultRecipient);
        message.setSubject("Task Status Updated");
        message.setText("Task ID " + taskId + " has changed status to: " + status);
        mailSender.send(message);
    }
}
