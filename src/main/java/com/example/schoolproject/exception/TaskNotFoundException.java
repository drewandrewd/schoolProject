package com.example.schoolproject.exception;

/**
 * Исключение, выбрасываемое при отсутствии задачи в базе данных.
 * <p>
 * Используется в сервисном слое, когда задача не найдена по ID или список задач пуст.
 */
public class  TaskNotFoundException extends RuntimeException {

    /**
     * Конструктор для случая, когда список задач пуст.
     */
    public TaskNotFoundException() {
        super("Tasks are not found");
    }

    /**
     * Конструктор для случая, когда задача с указанным ID не найдена.
     *
     * @param id идентификатор задачи
     */
    public TaskNotFoundException(Long id) {
        super("Task with id " + id + " is not found");
    }
}
