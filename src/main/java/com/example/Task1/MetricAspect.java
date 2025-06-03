package com.example.Task1;

import com.example.Task1.services.TimeLimitLogService;
import org.springframework.beans.factory.annotation.Value;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@Aspect
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MetricAspect {

    public final TimeLimitLogService timeLimitLogService;
    public MetricAspect(TimeLimitLogService timeLimitLogService) {
        this.timeLimitLogService = timeLimitLogService;
    }

    @Value("${metric.time-limit-millis}")
    private Long timeLimitMillis;

    @Around("@annotation(com.example.Task1.Metric)")
    public Object methodLimitedLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > timeLimitMillis) {
            timeLimitLogService.saveRow(joinPoint.getSignature().toShortString(), executionTime,timeLimitMillis);
        }
        return result;

    }

}
