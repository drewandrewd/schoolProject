package com.example.schoolproject.service;

import com.example.schoolproject.dto.TaskDTO;

import java.util.List;

/**
 * Интерфейс сервиса для управления задачами.
 */
public interface TaskService {

    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(Long id);
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
}
