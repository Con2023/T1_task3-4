package com.example.Task1.Services;

import com.example.Task1.Entities.TimeLimitExceedLog;
import com.example.Task1.Repositories.RepositoryTimeLimitExceedLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServiceTimeLimitLog {

    public final RepositoryTimeLimitExceedLog repositoryTimeLimitExceedLog;


    public ServiceTimeLimitLog(RepositoryTimeLimitExceedLog repositoryTimeLimitExceedLog) {
        this.repositoryTimeLimitExceedLog = repositoryTimeLimitExceedLog;
    }

    public void saveRow(String methodName, Long executionTime ,  Long timeLimit){
        TimeLimitExceedLog timeLimitExceedLog = new TimeLimitExceedLog();
        timeLimitExceedLog.setMethodName(methodName);
        timeLimitExceedLog.setExecutionTime(executionTime );
        timeLimitExceedLog.setTimeLimit(timeLimit);
        timeLimitExceedLog.setTimestamp(LocalDateTime.now());
        repositoryTimeLimitExceedLog.save(timeLimitExceedLog);
    }
}
