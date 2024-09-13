package com.example.SpringBootServicesLabs.repo;

import com.example.SpringBootServicesLabs.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocalAccountRepository implements AccountRepository {
    List<Account> accounts = new ArrayList<>();

    @Autowired
    public LocalAccountRepository() {
        Account account1 = new Account(1, "saving", 10520);
        Account account2 = new Account(2, "checking", 453.21);
        Account account3 = new Account(3, "saving", 1000);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public Account findAccountById(int id) {
        return accounts.stream()
                .filter(account -> account.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public void batchInsert(List<Account> accounts) {
        this.accounts.addAll(accounts);
    }
}