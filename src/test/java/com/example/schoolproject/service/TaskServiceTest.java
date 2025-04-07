package com.example.schoolproject.service;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.entity.Task;
import com.example.schoolproject.exception.TaskNotFoundException;
import com.example.schoolproject.mapper.MainMapper;
import com.example.schoolproject.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MainMapper mapper;

    @InjectMocks
    private TaskServiceImpl service;

    private Task task;
    private TaskDTO taskDTO;
    private Task updatedTask;
    private TaskDTO updatedTaskDTO;

    private static final Long ID = 1L;
    private static final String TASKS_NOT_FOUND = "Tasks are not found";
    private static final String TASK_NOT_FOUND = "Task with id " + ID + " is not found";

    @BeforeEach
    void setUp() {
        task = new Task(ID, "title", "description", 10L);
        taskDTO = new TaskDTO("title", "description", 10L);
        updatedTask = new Task(ID, "newTitle", "newDescription", 11L);
        updatedTaskDTO = new TaskDTO("newTitle", "newDescription", 11L);
    }

    @Test
    void shouldReturnTaskByIdWhenExist() {
        when(taskRepository.findById(ID)).thenReturn(Optional.of(task));
        when(mapper.toDTO(task)).thenReturn(taskDTO);
        TaskDTO result = service.getTaskById(ID);
        Assertions.assertNotNull(result);
        assertEquals(taskDTO, result);
        verify(taskRepository, times(1)).findById(ID);
    }

    @Test
    void shouldTrowExceptionWhenTaskNotFoundById() {
        when(taskRepository.findById(ID)).thenReturn(Optional.empty());
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.getTaskById(ID));
        verify(taskRepository).findById(any());
        assertEquals(TASK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReturnAllTasksWhenExist() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(mapper.toDTO(task)).thenReturn(taskDTO);
        List<TaskDTO> result = service.getAllTasks();
        Assertions.assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDTO, result.get(0));
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenTasksNotFound() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.getAllTasks());
        verify(taskRepository).findAll();
        assertEquals(TASKS_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldDeleteTaskWhenExist() {
        when(taskRepository.existsById(ID)).thenReturn(true);
        service.deleteTask(ID);
        verify(taskRepository, times(1)).existsById(ID);
        verify(taskRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldThrowExceptionInsteadDeleting() {
        when(taskRepository.existsById(ID)).thenReturn(false);
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.deleteTask(ID));
        verify(taskRepository).existsById(ID);
        verify(taskRepository, never()).deleteById(any());
        assertEquals(TASK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldCreateTask() {
        when(mapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(taskDTO);
        TaskDTO result = service.createTask(taskDTO);
        assertEquals(taskDTO, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldUpdateTask() {
        when(taskRepository.findById(ID)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(mapper.toDTO(updatedTask)).thenReturn(updatedTaskDTO);
        TaskDTO result = service.updateTask(task.getId(), updatedTaskDTO);
        assertEquals(updatedTaskDTO, result);
        verify(taskRepository, times(1)).save(updatedTask);
    }
}
