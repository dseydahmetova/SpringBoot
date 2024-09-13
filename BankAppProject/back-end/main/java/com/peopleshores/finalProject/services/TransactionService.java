package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.TransactionRequestDto;
import com.peopleshores.finalProject.dtos.TransactionResponseDto;
import com.peopleshores.finalProject.exceptions.InsufficientBalanceException;

import javax.security.auth.login.AccountNotFoundException;

public interface TransactionService {
    TransactionResponseDto postTransactionByType(TransactionRequestDto request) throws InsufficientBalanceException, AccountNotFoundException, InsufficientBalanceException;
}