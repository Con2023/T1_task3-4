package com.example.Task1.Services;

import com.example.Task1.DataSourceErrorLogAnnotation;
import com.example.Task1.Entities.Transaction;
import com.example.Task1.Repositories.RepositoryTransaction;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import com.example.Task1.Metric;

@Service
public class ServiceTransaction {

    private final RepositoryTransaction repoTransaction;

    public ServiceTransaction(RepositoryTransaction repoTransaction) {
        this.repoTransaction = repoTransaction;
    }

    @Metric
    public void slowMethod() {
        try {
            Thread.sleep(1500); // Задержка 1.5 секунды (если лимит = 1000 мс)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DataSourceErrorLogAnnotation
    public void createTransaction(Transaction transaction) {
        transaction.setTransactionTime(LocalDateTime.now());
        repoTransaction.save(transaction);
    }

    @DataSourceErrorLogAnnotation
    public void deleteTransactionById(Long id) {
        try {
            repoTransaction.deleteById(id);
        } catch (EmptyResultDataAccessException | NoSuchElementException ex) {
            throw new EntityNotFoundException("Transaction with id " + id + " not found", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot delete transaction with id " + id + " due to data integrity violation", ex);
        }
    }

    @DataSourceErrorLogAnnotation
    public void updateTransactionById(Long id, Transaction transaction) {
        try {
            Transaction newTransaction = repoTransaction.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Transaction with id " + id + " not found"));
            newTransaction.setTransactionTime(LocalDateTime.now());
            newTransaction.setAmount(transaction.getAmount());
            repoTransaction.save(newTransaction);
        } catch (ConstraintViolationException ex) {
            throw new RuntimeException("Validation failed for transaction update", ex);
        } catch (PersistenceException ex) {
            throw new RuntimeException("Persistence error during transaction update", ex);
        }
    }


    @DataSourceErrorLogAnnotation
    public Transaction getTransactionById(Long id) {
        try {
            return repoTransaction.findById(id).get();
        }
        catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Transaction with id " + id + " not found");
        }
    }

}
