package com.peopleshores.finalProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.peopleshores.finalProject.dtos.TransactionRequestDto;
import com.peopleshores.finalProject.dtos.TransactionResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.entities.TransactionType;
import com.peopleshores.finalProject.services.TransactionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    TransactionResponseDto responseDto;
    TransactionRequestDto requestDto;

    @MockBean
    TransactionService transactionService;

    Transaction transaction;
    Customer customer;
    Account account1, account2, account3;
    List accounts = new ArrayList();
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup() {
        account1 = new Account(12L, 4789, "saving", 500.0, 450.0, customer);
        account2 = new Account(13L, 4789, "checking", 200.0, 100.0, customer);
        accounts.add(account1);
        accounts.add(account2);
        transaction = new Transaction(
                1L,
                LocalDateTime.of(2024, 9, 3, 14, 30), 
                TransactionType.DEPOSIT, 
                1234567890L,
                1234,
                9876543210L,
                1234,
                500.00
        );
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        requestDto = new TransactionRequestDto();
        requestDto.setType(TransactionType.DEPOSIT);
        responseDto = new TransactionResponseDto(LocalDateTime.of(2024, 6, 8, 9, 52, 32), TransactionType.DEPOSIT ,
         1234567890L, 1234, 9876543210L, 1234, 500.00);
    }

    @Test
    void postTransactionByType() {

        try {
            when(transactionService.postTransactionByType(any(TransactionRequestDto.class))).thenReturn(responseDto);
            mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    responseDto
                )))
                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.time").value("08-06-2024 09:52:32 AM"))
                    .andExpect(jsonPath("$.type").value("DEPOSIT"))
                    .andExpect(jsonPath("$.fromAccount").value(1234567890L))
                    .andExpect(jsonPath("$.fromAccountSortCode").value(1234))
                    .andExpect(jsonPath("$.toAccount").value(9876543210L))
                    .andExpect(jsonPath("$.toAccountSortCode").value(1234))
                    .andExpect(jsonPath("$.amount").value(500.00));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

   }
}