package com.example.schoolproject.dto;

import com.example.schoolproject.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для представления задачи.
 * Используется в слоях контроллера и сервиса для передачи данных о задаче.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskDTO {

    private String title;
    private String description;
    private Long userId;
    private TaskStatus status;
}
