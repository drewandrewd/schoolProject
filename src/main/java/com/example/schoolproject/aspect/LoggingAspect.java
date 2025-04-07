package com.example.schoolproject.aspect;

import com.example.schoolproject.aspect.annotation.LogExecution;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Аспект для логирования выполнения методов, помеченных аннотацией {@link LogExecution}.
 * Логирует информацию до, после, при исключении и по времени выполнения метода.
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {


    /**
     * Логирует информацию перед выполнением метода.
     *
     * @param joinPoint точка соединения с деталями вызываемого метода
     */
    @LogExecution
    @Before("@annotation(com.example.schoolproject.aspect.annotation.LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Before method: {} with args {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Логирует результат после успешного выполнения метода.
     *
     * @param joinPoint точка соединения
     * @param result возвращаемое значение метода
     */
    @LogExecution
    @AfterReturning(pointcut = "@annotation(com.example.schoolproject.aspect.annotation.LogExecution)",
    returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("AfterReturning from method: {} with result: {}", joinPoint.getSignature().getName(), result);
    }

    /**
     * Логирует исключение, если метод выбрасывает ошибку.
     *
     * @param joinPoint точка соединения
     * @param exception исключение, выброшенное методом
     */
    @LogExecution
    @AfterThrowing(pointcut = "@annotation(com.example.schoolproject.aspect.annotation.LogExecution)",
    throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception thrown in method: {} with message: {}",
                joinPoint.getSignature().getName(),
                exception.getMessage(),
                exception);
    }

    /**
     * Логирует время выполнения метода.
     *
     * @param joinPoint точка соединения
     * @return результат выполнения метода
     * @throws Throwable проброс исключений из исходного метода
     */
    @LogExecution
    @Around("@annotation(com.example.schoolproject.aspect.annotation.LogExecution)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Around method: {} executed in {} ms", joinPoint.getSignature().getName(), elapsedTime);
            return result;
        } catch (Throwable ex) {
            log.error("Exception in Around advice: {}", ex.getMessage());
            throw ex;
        }
    }
}
