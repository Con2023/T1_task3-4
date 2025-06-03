package com.example.Task1.Entities;

import jakarta.persistence.*;

//Какая то ошибочка произошла с средой, поэтому Lombok не удалось подключить, все сеттеры и геттеры написаны через генератор( Надо разобраться

@Entity
@Table(name = "accounts")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private int balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public enum AccountType {
        ДЕБЕТ, КРЕДИТ
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
