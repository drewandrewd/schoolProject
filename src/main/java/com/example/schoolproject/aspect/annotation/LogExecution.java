package com.example.schoolproject.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для включения логирования через AOP.
 * <p>
 * Используется для маркировки методов, к которым применяется аспект {@link com.example.schoolproject.aspect.LoggingAspect}.
 * Логируются вызовы, успешное выполнение, ошибки и время выполнения.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecution {
}
