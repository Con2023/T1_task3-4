package com.example.Task1;

<<<<<<< HEAD
import com.example.Task1.entities.TimeLimitExceedLog;
import com.example.Task1.repositories.TimeLimitExceedLogRepository;
import com.example.Task1.services.TransactionService;
=======
import com.example.Task1.Entities.TimeLimitExceedLog;
import com.example.Task1.Repositories.RepositoryTimeLimitExceedLog;
import com.example.Task1.Services.ServiceTransaction;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
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
<<<<<<< HEAD
    private TransactionService transactionService;

    @Autowired
    private TimeLimitExceedLogRepository timeLimitExceedLogRepository;
=======
    private ServiceTransaction serviceTransaction;

    @Autowired
    private RepositoryTimeLimitExceedLog repositoryTimeLimitExceedLog;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d

    @Test
    void testMetricAspect() throws InterruptedException {
        // 1. Вызываем медленный метод
<<<<<<< HEAD
        transactionService.slowMethod();
=======
        serviceTransaction.slowMethod();
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d

        // 2. Даем время на сохранение (если нужно)
        Thread.sleep(100);

        // 3. Проверяем наличие записи в БД
<<<<<<< HEAD
        List<TimeLimitExceedLog> logs = timeLimitExceedLogRepository.findAll();
=======
        List<TimeLimitExceedLog> logs = repositoryTimeLimitExceedLog.findAll();
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
        assertFalse(logs.isEmpty(), "В БД должна быть запись о превышении времени");

    }
}