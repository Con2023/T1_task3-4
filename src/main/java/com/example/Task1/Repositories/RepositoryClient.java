package com.example.Task1.Repositories;

import com.example.Task1.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryClient extends JpaRepository<Client, Long>{

}
