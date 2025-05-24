package com.example.Task1;

import com.example.Task1.Entities.DataSourceErrorLog;
import com.example.Task1.Repositories.RepositoryDataSourceErrorLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.stream.Collectors;

//Самое интересное
//Прописываем аспект,
@Aspect
@Component
@Transactional(propagation = Propagation.REQUIRES_NEW) // Объявляет, что для каждого метода,
// где мы вызываем транзакцию будет новая сфера действия,
// таким образом ошибки одной выявленные в результате выполнения одного
// метода не будут влиять на другой.


public class DataSourceErrorLogAspect {

    private final RepositoryDataSourceErrorLog errorLogRepository;

    private static final Logger log = LoggerFactory.getLogger(DataSourceErrorLogAspect.class);
    //создаем логгер, чтобы через консоль тоже смотреть что происходит

    public DataSourceErrorLogAspect(RepositoryDataSourceErrorLog errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }
    //так как мы работаем с методами, а именно когда они выбрасывают исключения, то данный тип совета очень даже кстати
    @AfterThrowing(
            //определяет, что совет применяется к всем методам, помеченным аннотацией @DataSourceErrorLogAnnotation.
            pointcut = "@annotation(com.example.Task1.DataSourceErrorLogAnnotation)",
            //указывает, что в метод совета будет передано исключение, обозначение может быть любым
            throwing = "ex"
    )

    //метод для создании записи работы аспекта в бд, передаем место вызова и сообщение
    public void logError(JoinPoint joinPoint, Exception ex) {
        try {
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
            //выводим в консольку сообщение что произошла запись вот с такой ошибкой
            log.info("Saved error: {}", ex.getMessage());
        } catch (Exception e) {
            log.error("Failed!", e);
        }
    }
}
