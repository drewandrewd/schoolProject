package com.example.schoolproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Сущность "Task", представляющая задачу, закреплённую за пользователем.
 * Используется для хранения в базе данных.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Long userId;

    @Override
    public int hashCode() {
        return Objects.hash(title, description, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task thatTask = (Task) obj;
        return Objects.equals(title, thatTask.title)
                && Objects.equals(description, thatTask.description)
                && Objects.equals(userId, thatTask.userId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}
