package com.example.Task1;

import com.example.Task1.Entities.TimeLimitExceedLog;
import com.example.Task1.Repositories.RepositoryTimeLimitExceedLog;
import com.example.Task1.Services.ServiceTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "metric.time-limit-millis=1000" // Лимит времени для теста
})
public class MetricAspectTest {

    @Autowired
    private ServiceTransaction serviceTransaction;

    @Autowired
    private RepositoryTimeLimitExceedLog repositoryTimeLimitExceedLog;

    @Test
    void testMetricAspect() throws InterruptedException {
        // 1. Вызываем медленный метод
        serviceTransaction.slowMethod();

        // 2. Даем время на сохранение (если нужно)
        Thread.sleep(100);

        // 3. Проверяем наличие записи в БД
        List<TimeLimitExceedLog> logs = repositoryTimeLimitExceedLog.findAll();
        assertFalse(logs.isEmpty(), "В БД должна быть запись о превышении времени");

    }
}