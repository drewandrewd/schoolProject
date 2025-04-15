package com.example.schoolproject.dto;

import com.example.schoolproject.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для передачи информации об изменении статуса задачи.
 * <p>
 * Используется при отправке и получении сообщений в Kafka, содержащих ID задачи и её новый статус.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskStatusUpdateDTO {
    private Long taskId;
    private TaskStatus newStatus;
}
