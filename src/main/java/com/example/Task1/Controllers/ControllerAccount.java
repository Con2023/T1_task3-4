package com.example.Task1.Controllers;

import com.example.Task1.CachedAnnotation;
import com.example.Task1.Entities.Account;
import com.example.Task1.Services.ServiceAccount;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@ComponentScan
@RestController
@RequestMapping("/api/accounts")
public class ControllerAccount {

    private final ServiceAccount serviceAccount;

    public ControllerAccount(ServiceAccount serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    @CachedAnnotation
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return serviceAccount.getAccountById(id);
    }

    @CachedAnnotation
    @PostMapping("/save")
    public void createAccount(@RequestBody Account account) {
        serviceAccount.saveAccount(account);
    }

    @CachedAnnotation
    @PutMapping("/update/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody Account account) {
        serviceAccount.updateAccount(id, account);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable Long id) {
        serviceAccount.deleteAccountById(id);
    }
}
