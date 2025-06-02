package com.example.Task3.Repositories;

import com.example.Task3.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryAccount extends JpaRepository<Account, Long> {
}
