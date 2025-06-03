package com.example.Task3;

import com.example.Task3.entities.TimeLimitExceedLog;
import com.example.Task3.repositories.TimeLimitExceedLogRepository;
import com.example.Task3.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@TestPropertySource(properties = {
        "metric.time-limit-millis=1000" // Лимит времени для теста
})
public class MetricAspectTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TimeLimitExceedLogRepository timeLimitExceedLogRepository;

    @Test
    void testMetricAspect() throws InterruptedException {
        // 1. Вызываем медленный метод
        transactionService.slowMethod();

        // 2. Даем время на сохранение (если нужно)
        Thread.sleep(100);

        // 3. Проверяем наличие записи в БД
        List<TimeLimitExceedLog> logs = timeLimitExceedLogRepository.findAll();
        assertFalse(logs.isEmpty(), "В БД должна быть запись о превышении времени");

    }
}