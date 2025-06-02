package com.example.Task3;

import com.example.Task3.Entities.DataSourceErrorLog;
import com.example.Task3.Repositories.RepositoryDataSourceErrorLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

//Самое интересное
//Прописываем аспект,
@Aspect
@Component

// где мы вызываем транзакцию будет новая сфера действия,
// таким образом ошибки одной выявленные в результате выполнения одного
// метода не будут влиять на другой.

@Order(1)
public class DataSourceErrorLogAspect {

    @Value("${kafka.topic.metrics}")
    private String metricsTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RepositoryDataSourceErrorLog errorLogRepository;

    public DataSourceErrorLogAspect(RepositoryDataSourceErrorLog errorLogRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.errorLogRepository = errorLogRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    //так как мы работаем с методами, а именно когда они выбрасывают исключения, то данный тип совета очень даже кстати
    @AfterThrowing(
            //определяет, что совет применяется к всем методам, помеченным аннотацией @DataSourceErrorLogAnnotation.
            pointcut = "@annotation(com.example.Task3.DataSourceErrorLogAnnotation)",
            //указывает, что в метод совета будет передано исключение, обозначение может быть любым
            throwing = "ex"
    )

    //метод для создании записи работы аспекта в бд, передаем место вызова и сообщение
    public void logError(JoinPoint joinPoint, Exception ex) {
        String messageError = String.format("%s: %s", ex.getClass().getSimpleName(), ex.getMessage());
        try {
            Message<String> message = MessageBuilder
                    .withPayload(messageError)
                    .setHeader(KafkaHeaders.TOPIC, metricsTopic)
                    .setHeader(KafkaHeaders.KEY, "error-key")
                    .setHeader("errorType", "DATA_SOURCE")
                    .build();
            kafkaTemplate.send(message).get();

        } catch (Exception e) {
            saveToDatabase(joinPoint, ex);

        }
    }
    private void saveToDatabase(JoinPoint joinPoint, Exception ex) {
        DataSourceErrorLog errorLog = new DataSourceErrorLog();
        errorLog.setMessage(ex.getMessage());
        //StackTrace получается слишком большим, поэтому просто превращаю в поток и делаю лимит на 10 строк, превращаю в строку и объединяю
        errorLog.setStackTrace(Arrays.stream(ex.getStackTrace())
                .limit(10)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n")));
        //здесь пришлось вызывать спец метод и превращать в строку
        errorLog.setMethodSignature(joinPoint.getSignature().toShortString());
        errorLogRepository.save(errorLog);
    }
}
