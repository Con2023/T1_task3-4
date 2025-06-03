package com.example.Task1;

<<<<<<< HEAD
import com.example.Task1.services.TimeLimitLogService;
=======
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
import org.springframework.beans.factory.annotation.Value;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@Aspect
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MetricAspect {

<<<<<<< HEAD
    public final TimeLimitLogService timeLimitLogService;
    public MetricAspect(TimeLimitLogService timeLimitLogService) {
        this.timeLimitLogService = timeLimitLogService;
=======
    public final ServiceTimeLimitLog serviceTimeLimitLog;
    public MetricAspect(ServiceTimeLimitLog serviceTimeLimitLog) {
        this.serviceTimeLimitLog = serviceTimeLimitLog;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
    }

    @Value("${metric.time-limit-millis}")
    private Long timeLimitMillis;

    @Around("@annotation(com.example.Task1.Metric)")
    public Object methodLimitedLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > timeLimitMillis) {
<<<<<<< HEAD
            timeLimitLogService.saveRow(joinPoint.getSignature().toShortString(), executionTime,timeLimitMillis);
=======
            serviceTimeLimitLog.saveRow(joinPoint.getSignature().toShortString(), executionTime,timeLimitMillis);
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
        }
        return result;

    }

}
