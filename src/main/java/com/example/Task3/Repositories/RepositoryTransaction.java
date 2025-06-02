package com.example.Task3.Repositories;

import com.example.Task3.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTransaction extends JpaRepository<Transaction, Long> {
    void deleteByAccountId(Long id);
}
