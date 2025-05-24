package com.example.Task1.config;

import com.example.Task1.Entities.Account;
import com.example.Task1.Entities.Client;
import com.example.Task1.Entities.Transaction;
import com.example.Task1.Repositories.RepositoryAccount;
import com.example.Task1.Repositories.RepositoryClient;
import com.example.Task1.Repositories.RepositoryTransaction;
import com.example.Task1.Services.ServiceAccount;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;

//Генератор данных. Через зависимости устанавливает взаимосвязь с методами CRUD и выполняет дейстивия по заполнению БД. Случайными! данными.

@Component
public class TestData implements CommandLineRunner {

    private final RepositoryClient clientRepository;
    private final RepositoryAccount accountRepository;
    private final ServiceAccount serviceAccount;
    private final RepositoryTransaction transactionRepository;

    public TestData(RepositoryClient clientRepository,
                    RepositoryAccount accountRepository, ServiceAccount serviceAccount,
                    RepositoryTransaction transactionRepository) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.serviceAccount = serviceAccount;
        this.transactionRepository = transactionRepository;
    }

    private final Random random = new Random();

    private final List<String> firstNames = Arrays.asList(
            "Иван", "Петр", "Сергей", "Алексей", "Дмитрий",
            "Анна", "Елена", "Ольга", "Мария", "Наталья"
    );

    private final List<String> lastNames = Arrays.asList(
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Васильев", "Попов", "Соколов", "Михайлов", "Новиков"
    );

    private final List<String> middleNames = Arrays.asList(
            "Иванович", "Петрович", "Сидорович", "Смирнович", "Кузнецович",
            "Васильевич", "Попович", "Соколович", "Михайлович", "Новикович"
    );

    @Override
    public void run(String... args) {
        if(transactionRepository.count() == 0) {
            generateData();
        }

    }
    private void generateData() {
        for (int i = 0; i < 20; i++) {
            Client client = new Client();
            client.setFirstName(firstNames.get(random.nextInt(firstNames.size())));
            client.setLastName(lastNames.get(random.nextInt(lastNames.size())));
            client.setMiddleName(middleNames.get(random.nextInt(middleNames.size())));
            clientRepository.save(client);

            int countAccounts = random.nextInt(2);
            for (int j = 0; j < countAccounts; j++) {
                Account account = new Account();
                account.setClient(client);
                account.setAccountType(serviceAccount.getRandomAccountType());
                account.setBalance(random.nextInt(100000));
                accountRepository.save(account);

                int transactionCount = random.nextInt(5)+2;
                for (int k = 0; k < transactionCount; k++) {
                    Transaction transaction = new Transaction();
                    transaction.setAccount(account);
                    transaction.setAmount(random.nextInt(1000) * (random.nextBoolean() ? 1 : -1));
                    transaction.setTransactionTime(LocalDateTime.now());
                    transactionRepository.save(transaction);
                }
            }
        }
    }
}

