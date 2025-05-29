package com.example.Task1.Repositories;

import com.example.Task1.Entities.TimeLimitExceedLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTimeLimitExceedLog extends JpaRepository<TimeLimitExceedLog, Long> {
}
