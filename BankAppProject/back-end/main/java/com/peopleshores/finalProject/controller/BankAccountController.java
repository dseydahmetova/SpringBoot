package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sortCode")
@CrossOrigin
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

     @GetMapping
     public int getBankSortCode() {
         return bankAccountService.getAccountSortCode();
     }
}


