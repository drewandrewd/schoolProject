package com.example.schoolproject.service;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import com.example.schoolproject.entity.Task;
import com.example.schoolproject.entity.TaskStatus;
import com.example.schoolproject.exception.TaskNotFoundException;
import com.example.schoolproject.kafka.KafkaTaskProducer;
import com.example.schoolproject.mapper.MainMapper;
import com.example.schoolproject.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private KafkaTaskProducer kafkaTaskProducer;

    @Mock
    private MainMapper mapper;

    private static final Long ID = 1L;
    private static final String TASKS_NOT_FOUND = "Tasks are not found";
    private static final String TASK_NOT_FOUND = "Task with id " + ID + " is not found";

    @BeforeEach
    void setUp() {
    }

    private Task getCreatedTask() {
        return new Task(ID, "title", "description", 10L, TaskStatus.TODO);
    }

    private TaskDTO getCreatedTaskDTO() {
        return new TaskDTO("title", "description", 10L, TaskStatus.TODO);
    }

    private Task getUpdatedTask() {
        return new Task(ID, "newTitle", "newDescription", 11L, TaskStatus.DONE);
    }

    private TaskDTO getUpdatedTaskDTO() {
        return new TaskDTO("newTitle", "newDescription", 11L, TaskStatus.DONE);
    }

    private TaskStatusUpdateDTO getTaskStatusUpdateDTO() {
        return new TaskStatusUpdateDTO(ID, TaskStatus.DONE);
    }

    private TaskService getTaskService() {
        return new TaskServiceImpl(taskRepository, mapper, kafkaTaskProducer);
    }

    @Test
    void shouldReturnTaskByIdWhenExist() {
        Task task = getCreatedTask();
        TaskDTO taskDTO = getCreatedTaskDTO();
        TaskService service = getTaskService();
        when(taskRepository.findById(ID)).thenReturn(Optional.of(task));
        when(mapper.toDTO(task)).thenReturn(taskDTO);
        TaskDTO result = service.getTaskById(ID);
        Assertions.assertNotNull(result);
        assertEquals(taskDTO, result);
        verify(taskRepository, times(1)).findById(ID);
    }

    @Test
    void shouldTrowExceptionWhenTaskNotFoundById() {
        TaskService service = getTaskService();
        when(taskRepository.findById(ID)).thenReturn(Optional.empty());
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.getTaskById(ID));
        verify(taskRepository).findById(any());
        assertEquals(TASK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReturnAllTasksWhenExist() {
        Task task = getCreatedTask();
        TaskDTO taskDTO = getCreatedTaskDTO();
        TaskService service = getTaskService();
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
        TaskService service = getTaskService();
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.getAllTasks());
        verify(taskRepository).findAll();
        assertEquals(TASKS_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldDeleteTaskWhenExist() {
        TaskService service = getTaskService();
        when(taskRepository.existsById(ID)).thenReturn(true);
        service.deleteTask(ID);
        verify(taskRepository, times(1)).existsById(ID);
        verify(taskRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldThrowExceptionInsteadDeleting() {
        TaskService service = getTaskService();
        when(taskRepository.existsById(ID)).thenReturn(false);
        TaskNotFoundException exception = Assertions.assertThrows(TaskNotFoundException.class, () -> service.deleteTask(ID));
        verify(taskRepository).existsById(ID);
        verify(taskRepository, never()).deleteById(any());
        assertEquals(TASK_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldCreateTask() {
        Task task = getCreatedTask();
        TaskDTO taskDTO = getCreatedTaskDTO();
        TaskService service = getTaskService();
        when(mapper.toEntity(taskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(mapper.toDTO(task)).thenReturn(taskDTO);
        TaskDTO result = service.createTask(taskDTO);
        assertEquals(taskDTO, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void shouldUpdateTask() {
        Task task = getCreatedTask();
        TaskService service = getTaskService();
        Task updatedTask = getUpdatedTask();
        TaskDTO updatedTaskDTO = getUpdatedTaskDTO();
        TaskStatusUpdateDTO taskStatusUpdateDTO = getTaskStatusUpdateDTO();
        when(taskRepository.findById(ID)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(mapper.toDTO(updatedTask)).thenReturn(updatedTaskDTO);
        when(mapper.toStatusUpdateDTO(updatedTask)).thenReturn(taskStatusUpdateDTO);
        TaskDTO result = service.updateTask(task.getId(), updatedTaskDTO);
        assertEquals(updatedTaskDTO, result);
        verify(taskRepository, times(1)).save(updatedTask);
        verify(kafkaTaskProducer).sendTo(any(), eq(taskStatusUpdateDTO));
    }
}
