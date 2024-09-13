package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.dtos.TransactionRequestDto;
import com.peopleshores.finalProject.dtos.TransactionResponseDto;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.services.AccountService;
import com.peopleshores.finalProject.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RequestMapping("/transaction")
@RestController
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private AccountService accountService;

    @PostMapping
    public TransactionResponseDto postTransaction(@RequestBody TransactionRequestDto request) throws AccountNotFoundException {
        return transactionService.postTransactionByType(request);
    }

    @GetMapping(":account={id}")
    public List<Transaction> getAccountTransactions(@PathVariable("id") Long id){
        return accountService.getAllTransactionsForAccount(id);
    }

}
