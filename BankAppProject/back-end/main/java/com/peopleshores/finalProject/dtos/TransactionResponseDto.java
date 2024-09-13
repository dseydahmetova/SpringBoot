package com.peopleshores.finalProject.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.peopleshores.finalProject.entities.TransactionType;
import java.time.LocalDateTime;

public record TransactionResponseDto(
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a")
        LocalDateTime time,
        TransactionType type,
        Long fromAccount,
        Integer fromAccountSortCode,
        Long toAccount,
        Integer toAccountSortCode,
        Double amount
) {
}