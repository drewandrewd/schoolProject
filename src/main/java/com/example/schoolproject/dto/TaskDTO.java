package com.example.schoolproject.dto;

import com.example.schoolproject.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для представления задачи.
 * Используется в слоях контроллера и сервиса для передачи данных о задаче.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private Long userId;
    private TaskStatus status;
}
