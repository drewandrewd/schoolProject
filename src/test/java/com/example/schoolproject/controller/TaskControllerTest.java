package com.example.schoolproject.controller;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.entity.Task;
import com.example.schoolproject.entity.TaskStatus;
import com.example.schoolproject.integration.PostgresContainer;
import com.example.schoolproject.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskControllerTest extends PostgresContainer {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository repository;

    private Long savedTaskId;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        Task saved = repository.save(getTask());
        savedTaskId = saved.getId();
    }

    private Task getTask() {
        return new Task(null, "title", "description", 10L, TaskStatus.TODO);
    }

    private TaskDTO getTaskDTO() {
        return new TaskDTO("title", "description", 10L, TaskStatus.TODO);
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].userId").value(10L));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        mockMvc.perform(get("/tasks/" + savedTaskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getTaskDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        mockMvc.perform(put("/tasks/" + savedTaskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTaskDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/" + savedTaskId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        mockMvc.perform(get("/tasks/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task with id 99999 is not found"));
    }
}
