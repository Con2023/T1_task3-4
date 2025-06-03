package com.example.Task3.repositories;

import com.example.Task3.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    void deleteByAccountId(Long id);
}
