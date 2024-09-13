package com.peopleshores.finalProject.services;

import org.junit.jupiter.api.Test;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.entities.TransactionType;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

class RealTransactionServiceTest {
    Transaction transaction;
    Customer customer;
    Account account1, account2, account3;
    List accounts = new ArrayList();

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
    }

    @Test
    void getTransactionByType() {
        TransactionType type = transaction.getType();
        assertEquals(TransactionType.DEPOSIT, type);
    }

}