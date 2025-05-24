package com.example.Task1.Services;

import com.example.Task1.DataSourceErrorLogAnnotation;
import com.example.Task1.Entities.Account;
import com.example.Task1.Repositories.RepositoryAccount;
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

    public ServiceAccount(RepositoryAccount repositoryAccount) {
        this.repositoryAccount = repositoryAccount;
    }

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

    @DataSourceErrorLogAnnotation
    public void deleteAccountById(Long id) {
        if (!repositoryAccount.existsById(id)) {
            throw new RuntimeException("Account with id " + id + " not found");
        }
        repositoryAccount.deleteById(id);
    }

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