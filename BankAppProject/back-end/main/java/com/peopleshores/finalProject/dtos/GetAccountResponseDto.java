package com.peopleshores.finalProject.dtos;

import com.peopleshores.finalProject.entities.Transaction;

import java.util.List;

public record GetAccountResponseDto(
        Long number,
        Integer sortCode,
        String name,
        Double openingBalance,
        List<Transaction> transactions,
        Double balance,
        Long customer
) {
}