package com.example.demo.services;

import com.example.demo.entities.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    List<Account> retrieveAllAccounts();
}
