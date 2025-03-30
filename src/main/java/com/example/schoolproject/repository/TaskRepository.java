package com.example.schoolproject.repository;

import com.example.schoolproject.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для доступа к данным задач ({@link Task}).
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
