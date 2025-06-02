package com.example.Task3.Controllers;

import com.example.Task3.CachedAnnotation;
import com.example.Task3.Entities.Transaction;
import com.example.Task3.Metric;
import com.example.Task3.Services.ServiceTransaction;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transactions")
public class ControllerTransaction {

    private final ServiceTransaction serviceTransaction;

    public ControllerTransaction(ServiceTransaction serviceTransaction) {
        this.serviceTransaction = serviceTransaction;
    }

    @GetMapping("/check")
    public void slowMethod() {
        serviceTransaction.slowMethod();
    }


    @Metric
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return serviceTransaction.getTransactionById(id);
    }


    @Metric
    @PutMapping("/update/{id}")
    public  void updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        serviceTransaction.updateTransactionById(id, transaction);
    }

    @Metric
    @DeleteMapping("/delete/{id}")
    public  void deleteTransaction(@PathVariable Long id) {
        serviceTransaction.deleteTransactionById(id);
    }

    @Metric
    @PostMapping("/save")
    public void createTransaction(@RequestBody Transaction transaction) {
        serviceTransaction.createTransaction(transaction);
    }

}
