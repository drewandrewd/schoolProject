package com.example.schoolproject.mapper;

import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.entity.Task;
import org.mapstruct.Mapper;

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
}
