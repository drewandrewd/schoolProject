package com.example.schoolproject.controller;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    private TaskDTO taskDTO;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskDTO = new TaskDTO("title", "description", 10L);
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        when(service.getAllTasks()).thenReturn(List.of(taskDTO));
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].userId").value(10L));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        when(service.getTaskById(1L)).thenReturn(taskDTO);
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldCreateTask() throws Exception {
        when(service.createTask(any(TaskDTO.class))).thenReturn(taskDTO);
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(service.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(taskDTO);
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.userId").value(10L));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(service).deleteTask(1L);
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
