package com.example.SpringBootServicesLabs.services;

import com.example.SpringBootServicesLabs.entities.Account;

import java.util.List;

public interface AccountService {
    List<Account> retrieveAllAccounts();

    void addBatchAccounts(List<Account> account);

    void addAccount(Account account);

    Account retrieveAccountById(int id);
}
