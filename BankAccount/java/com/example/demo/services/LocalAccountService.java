package com.example.demo.services;

import com.example.demo.entities.Account;
import com.example.demo.repos.AccountRepository;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalAccountService implements AccountService{

    private final AccountRepository accountRepository;
//    private final Logger logger = LoggerFactory.getLogger(ExampleServiceDev.class);
    public LocalAccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> retrieveAllAccounts() {
//        try{
//            logger.debug(accountRepository.getAllAccounts());
//        }catch(Exception e){
//            logger.error(e.getMessage());
//        }
        return accountRepository.getAllAccounts();
    }
}
