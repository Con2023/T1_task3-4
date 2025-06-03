package com.example.Task1.repositories;

import com.example.Task1.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    void deleteByAccountId(Long id);
}
