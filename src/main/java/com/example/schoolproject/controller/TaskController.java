package com.example.schoolproject.controller;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для управления задачами.
 * Предоставляет CRUD-интерфейс через HTTP.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    /**
     * Получить список всех задач.
     *
     * @return список задач в формате {@link TaskDTO}
     */
    @Operation(summary = "Get all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tasks found")
    })
    @GetMapping
    public Iterable<TaskDTO> getTasks() {
        return service.getAllTasks();
    }


    /**
     * Получить задачу по ID.
     *
     * @param id идентификатор задачи
     * @return задача в формате {@link TaskDTO}
     */
    @Operation(summary = "Get a task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    /**
     * Создать новую задачу.
     *
     * @param taskDTO данные новой задачи
     * @return созданная задача в формате {@link TaskDTO}
     */
    @Operation(summary = "Create a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return service.createTask(taskDTO);
    }

    /**
     * Обновить существующую задачу.
     *
     * @param id идентификатор обновляемой задачи
     * @param taskDTO новые данные задачи
     * @return обновлённая задача в формате {@link TaskDTO}
     */
    @Operation(summary = "Update an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return service.updateTask(id, taskDTO);
    }

    /**
     * Удалить задачу по ID.
     *
     * @param id идентификатор задачи для удаления
     * @return HTTP 204 при успешном удалении
     */
    @Operation(summary = "Delete a task by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
