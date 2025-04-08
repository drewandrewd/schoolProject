package com.example.schoolproject.mapper;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.dto.TaskStatusUpdateDTO;
import com.example.schoolproject.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Маппер для преобразования между сущностью {@link Task} и DTO {@link TaskDTO}.
 * <p>
 * Используется библиотека MapStruct для автоматической генерации реализаций.
 */
@Mapper(componentModel = "spring")
public interface MainMapper {


    /**
     * Преобразует DTO в сущность.
     *
     * @param dto DTO задачи
     * @return сущность {@link Task}
     */
    Task toEntity(TaskDTO dto);

    /**
     * Преобразует сущность в DTO.
     *
     * @param entity сущность задачи
     * @return DTO {@link TaskDTO}
     */
    TaskDTO toDTO(Task entity);

    /**
     * Преобразует сущность Task в DTO для Kafka.
     *
     * @param task сущность задачи
     * @return DTO со статусом
     */
    @Mappings({
            @Mapping(source = "id", target = "taskId"),
            @Mapping(source = "status", target = "newStatus")
    })
    TaskStatusUpdateDTO toStatusUpdateDTO(Task task);
}
