package com.example.SpringBootServicesLabs.controllers;

import com.example.SpringBootServicesLabs.entities.Account;
import com.example.SpringBootServicesLabs.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController  {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.retrieveAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable int id) {
        return accountService.retrieveAccountById(id);
    }

    @PostMapping
    public ResponseEntity<String> addNewAccount(@RequestBody Account account) {
        accountService.addAccount(account);
        return new ResponseEntity<>("Account successfully added", HttpStatus.CREATED);
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addListAccounts(@RequestBody List<Account> account) {
        accountService.addBatchAccounts(account);
        return new ResponseEntity<>("Accounts were successfully added", HttpStatus.CREATED);
    }
}
