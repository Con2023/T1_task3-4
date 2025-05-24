package com.example.Task1.Repositories;

import com.example.Task1.Entities.DataSourceErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryDataSourceErrorLog extends JpaRepository<DataSourceErrorLog, Long> {
}
