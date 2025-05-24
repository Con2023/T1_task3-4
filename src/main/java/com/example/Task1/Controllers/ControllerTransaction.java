package com.example.Task1.Controllers;

import com.example.Task1.Entities.Transaction;
import com.example.Task1.Services.ServiceTransaction;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
public class ControllerTransaction {

    private final ServiceTransaction serviceTransaction;

    public ControllerTransaction(ServiceTransaction serviceTransaction) {
        this.serviceTransaction = serviceTransaction;
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return serviceTransaction.getTransactionById(id);
    }

    @PutMapping("/update/{id}")
    public  void updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        serviceTransaction.updateTransactionById(id, transaction);
    }

    @DeleteMapping("/delete/{id}")
    public  void deleteTransaction(@PathVariable Long id) {
        serviceTransaction.deleteTransactionById(id);
    }

    @PostMapping("/save")
    public void createTransaction(@RequestBody Transaction transaction) {
        serviceTransaction.createTransaction(transaction);
    }

}
