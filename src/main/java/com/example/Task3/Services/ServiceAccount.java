package com.example.Task3.Services;

import com.example.Task3.DataSourceErrorLogAnnotation;
import com.example.Task3.Entities.Account;
import com.example.Task3.Metric;
import com.example.Task3.Repositories.RepositoryAccount;
import com.example.Task3.Repositories.RepositoryTransaction;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.util.Random;


//В сервисе приписываем точки входа на которые будет реагировать аспект, то есть создаем исключения, которые будут записаны в бд по условию.
//Расписываем основные операции CRUD, задействуем репозитории для получения данных.

@Service
public class ServiceAccount {
    Random random = new Random();

    private final RepositoryAccount repositoryAccount;
    private final RepositoryTransaction repositoryTransaction;

    public ServiceAccount(RepositoryAccount repositoryAccount, RepositoryTransaction repositoryTransaction) {
        this.repositoryAccount = repositoryAccount;
        this.repositoryTransaction = repositoryTransaction;
    }

    @Metric
    @DataSourceErrorLogAnnotation
    public Account getAccountById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0) {
            throw new RuntimeException("ID must be positive", null);
        }
        return repositoryAccount.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));
    }

    @Metric
    @DataSourceErrorLogAnnotation
    public void saveAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (account.getClient() == null) {
            throw new IllegalStateException("Account must have a client");
        }
        try {
            repositoryAccount.save(account);
        } catch (ConstraintViolationException ex) {
            throw new RuntimeException("Validation failed: " + ex.getMessage(), ex);
        }
    }

    @Metric
    @DataSourceErrorLogAnnotation
    public void deleteAccountById(Long id) {
        Account account = repositoryAccount.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found")); // Исключение пробрасывается

        repositoryTransaction.deleteByAccountId(id);
        repositoryAccount.delete(account);
    }

    @Metric
    @DataSourceErrorLogAnnotation
    public void updateAccount(Long id, Account account) {
        Account existingAccount = repositoryAccount.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setBalance(account.getBalance());
        try {
            repositoryAccount.save(existingAccount);
        } catch (PersistenceException ex) {
            throw new RuntimeException("Failed to update account with id " + id, ex);
        }

    }
    //метод понадобиться при генерации данных для определения рандомного типа счета
    public Account.AccountType getRandomAccountType() {
        return Account.AccountType.valueOf(random.nextBoolean() ? "ДЕБЕТ" : "КРЕДИТ");
    }
}

//EntityNotFoundException - Запись не найден
//NoSuchElementException - Вызов .get() у пустого Optional
//DataIntegrityViolationException - Нарушение ограничений БД (FK, уникальность)
//ConstraintViolationException - Обрабатывать при сохранении и обновлении
//PersistenceException - Общие ошибки JPA/Hibernate	Обрабатывать для непредвиденных ошибок
//IllegalArgumentException - Некорректные аргументы	Обрабатывать для защиты от ошибок вызова