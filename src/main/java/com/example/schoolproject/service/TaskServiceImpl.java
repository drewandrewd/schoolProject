package com.example.schoolproject.service;

import com.example.schoolproject.aspect.annotation.LogExecution;
import com.example.schoolproject.dto.TaskDTO;
import com.example.schoolproject.entity.Task;
import com.example.schoolproject.exception.TaskNotFoundException;
import com.example.schoolproject.mapper.MainMapper;
import com.example.schoolproject.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный слой для управления задачами.
 * Реализует бизнес-логику CRUD-операций над сущностью {@link Task}.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final MainMapper mapper;

    /**
     * Возвращает список всех задач.
     *
     * @return список задач в формате {@link TaskDTO}
     * @throws TaskNotFoundException если задачи отсутствуют
     */
    @LogExecution
    @Transactional
    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = repository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }
        return tasks.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает задачу по идентификатору.
     *
     * @param id идентификатор задачи
     * @return задача в формате {@link TaskDTO}
     * @throws TaskNotFoundException если задача не найдена
     */
    @LogExecution
    @Override
    public TaskDTO getTaskById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * Создаёт новую задачу.
     *
     * @param taskDTO DTO с данными новой задачи
     * @return созданная задача в формате {@link TaskDTO}
     */
    @LogExecution
    @Transactional
    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = mapper.toEntity(taskDTO);
        return mapper.toDTO(repository.save(task));
    }

    /**
     * Обновляет существующую задачу по ID.
     *
     * @param id идентификатор обновляемой задачи
     * @param taskDTO новые данные задачи
     * @return обновлённая задача в формате {@link TaskDTO}
     * @throws TaskNotFoundException если задача не найдена
     */
    @LogExecution
    @Transactional
    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task currTask = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        currTask.setDescription(taskDTO.getDescription());
        currTask.setTitle(taskDTO.getTitle());
        currTask.setUserId(taskDTO.getUserId());
        return mapper.toDTO(repository.save(currTask));
    }

    /**
     * Удаляет задачу по ID.
     *
     * @param id идентификатор задачи для удаления
     * @throws TaskNotFoundException если задача не найдена
     */
    @LogExecution
    @Transactional
    @Override
    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
