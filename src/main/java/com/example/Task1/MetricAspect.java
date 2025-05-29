package com.example.Task1;

import com.example.Task1.Services.ServiceTimeLimitLog;
import com.example.Task1.Services.ServiceTransaction;
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

    public final ServiceTimeLimitLog serviceTimeLimitLog;
    public MetricAspect(ServiceTimeLimitLog serviceTimeLimitLog) {
        this.serviceTimeLimitLog = serviceTimeLimitLog;
    }

    @Value("${metric.time-limit-millis}")
    private Long timeLimitMillis;

    @Around("@annotation(com.example.Task1.Metric)")
    public Object methodLimitedLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > timeLimitMillis) {
            serviceTimeLimitLog.saveRow(joinPoint.getSignature().toShortString(), executionTime,timeLimitMillis);
        }
        return result;

    }

}
