package com.example.Task1;

<<<<<<< HEAD
import com.example.Task1.entities.DataSourceErrorLog;
import com.example.Task1.repositories.DataSourceErrorLogRepository;
=======
import com.example.Task1.Entities.DataSourceErrorLog;
import com.example.Task1.Repositories.RepositoryDataSourceErrorLog;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
<<<<<<< HEAD

=======
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
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

<<<<<<< HEAD
    private final DataSourceErrorLogRepository errorLogRepository;
=======
    private final RepositoryDataSourceErrorLog errorLogRepository;
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d

    private static final Logger log = LoggerFactory.getLogger(DataSourceErrorLogAspect.class);
    //создаем логгер, чтобы через консоль тоже смотреть что происходит

<<<<<<< HEAD
    public DataSourceErrorLogAspect(DataSourceErrorLogRepository errorLogRepository) {
=======
    public DataSourceErrorLogAspect(RepositoryDataSourceErrorLog errorLogRepository) {
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
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
<<<<<<< HEAD
            try {
                errorLogRepository.save(errorLog);
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
=======
            errorLogRepository.save(errorLog);
>>>>>>> ad223588ce4f39148a8cdf0697c063891461f79d
            //выводим в консольку сообщение что произошла запись вот с такой ошибкой
            log.info("Saved error: {}", ex.getMessage());
        } catch (Exception e) {
            log.error("Failed!", e);
        }
    }
}
