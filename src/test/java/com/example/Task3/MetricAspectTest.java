package com.example.Task3;

import com.example.Task3.Entities.TimeLimitExceedLog;
import com.example.Task3.Repositories.RepositoryTimeLimitExceedLog;
import com.example.Task3.Services.ServiceTransaction;
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
    private ServiceTransaction serviceTransaction;

    @Autowired
    private RepositoryTimeLimitExceedLog repositoryTimeLimitExceedLog;

    @Test
    void testMetricAspect() throws InterruptedException {
        serviceTransaction.slowMethod();
        Thread.sleep(100);
        List<TimeLimitExceedLog> logs = repositoryTimeLimitExceedLog.findAll();
        assertFalse(logs.isEmpty(), "В БД должна быть запись о превышении времени");

    }
}