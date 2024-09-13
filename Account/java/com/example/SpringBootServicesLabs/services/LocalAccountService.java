package com.example.SpringBootServicesLabs.services;

import com.example.SpringBootServicesLabs.entities.Account;
import com.example.SpringBootServicesLabs.repo.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalAccountService implements AccountService {

    private final AccountRepository accountRepository;

    public LocalAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> retrieveAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    @Override
    public void addBatchAccounts(List<Account> account) {
        accountRepository.batchInsert(account);
    }

    @Override
    public void addAccount(Account account) {
        accountRepository.saveAccount(account);
    }

    @Override
    public Account retrieveAccountById(int id) {
        return accountRepository.findAccountById(id);
    }
}
