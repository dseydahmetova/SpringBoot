package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.dtos.CreateAccountRequestDto;
import com.peopleshores.finalProject.dtos.GetAccountResponseDto;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<GetAccountResponseDto> getAccounts() {
        return accountService.getAllAccounts();
    }


    @GetMapping("/{id}")
    public GetAccountResponseDto getAccount(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public GetAccountResponseDto addNewAccount(@RequestBody CreateAccountRequestDto request) {
        return accountService.createAccount(request);
    }

    @DeleteMapping("/{id}")
    public Double removeAccount(@PathVariable Long id) {
       return accountService.deleteAccount(id);
    }


}
