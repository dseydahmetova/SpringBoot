package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.CreateAccountRequestDto;
import com.peopleshores.finalProject.dtos.GetAccountResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Transaction;

import java.util.List;

public interface AccountService {
    List<GetAccountResponseDto> getAllAccounts();

    GetAccountResponseDto createAccount(CreateAccountRequestDto request);

    GetAccountResponseDto getAccountById(Long id);

    Double deleteAccount(Long id);

    List<Transaction> getAllTransactionsForAccount(Long accountNumber);

}
