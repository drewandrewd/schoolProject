package com.example.schoolproject.mapper;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import com.example.schoolproject.entity.Task;
import com.example.schoolproject.entity.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MainMapperTest {

    private MainMapper mapper = Mappers.getMapper(MainMapper.class);

    private Task getCreatedTask() {
        return new Task(1L, "title", "description", 10L, TaskStatus.TODO);
    }

    private TaskDTO getCreatedTaskDTO() {
        return new TaskDTO("title", "description", 10L, TaskStatus.TODO);
    }

    private TaskStatusUpdateDTO getTaskStatusUpdateDTO() {
        return new TaskStatusUpdateDTO(1L, TaskStatus.TODO);
    }

    @Test
    void shouldReturnTaskDTO() {
        TaskDTO result = mapper.toDTO(getCreatedTask());
        Assertions.assertEquals(result, getCreatedTaskDTO());
    }

    @Test
    void shouldReturnTaskEntity() {
        Task result = mapper.toEntity(getCreatedTaskDTO());
        Assertions.assertEquals(result, getCreatedTask());
    }

    @Test
    void shouldReturnTaskStatusUpdateDTO() {
        TaskStatusUpdateDTO result = mapper.toStatusUpdateDTO(getCreatedTask());
        Assertions.assertEquals(result, getTaskStatusUpdateDTO());
    }
}
