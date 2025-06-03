package com.example.Task3.repositories;

import com.example.Task3.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
