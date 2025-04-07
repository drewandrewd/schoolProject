package com.example.schoolproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Swagger/OpenAPI для генерации документации REST API.
 */
@Configuration
public class SwaggerConfig {


    /**
     * Бин для настройки OpenAPI-документации.
     * @return объект {@link OpenAPI} для конфигурации Swagger
     */
    @Bean
    public OpenAPI getSwagger() {
        return new OpenAPI();
    }
}
