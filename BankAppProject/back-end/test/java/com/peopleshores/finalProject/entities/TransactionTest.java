package com.peopleshores.finalProject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionTest {

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
    void getId() {
        assertEquals(1L, transaction.getId());
    }

    @Test
    void setId() {
        transaction.setId(2L);
        assertEquals(2L, transaction.getId());
    }

    @Test
    void getTime() {
        LocalDateTime expectedTime = LocalDateTime.of(2024, 9, 3, 14, 30);
        LocalDateTime actualTime = transaction.getTime();
        assertEquals(expectedTime, actualTime);
    }

    @Test
    void setTime() {
        LocalDateTime expectedTime = LocalDateTime.of(2024, 9, 3, 14, 30);
        LocalDateTime actualTime = transaction.getTime();
        assertEquals(expectedTime, actualTime);
    }

    @Test
    void getTransactionType() {
        TransactionType type = transaction.getType();
        assertEquals(TransactionType.DEPOSIT, type);
    }

    @Test
    void setTransactionType() {
        TransactionType type = transaction.getType();
        assertEquals(TransactionType.DEPOSIT, type);
    }

    @Test
    void getFromAccount() {
        Long fromAccount = transaction.getFromAccount();
        assertEquals(1234567890L, fromAccount);
    }

    @Test
    void setFromAccount() {
        Long fromAccount = transaction.getFromAccount();
        assertEquals(1234567890L, fromAccount);
    }

    @Test
    void getFromAccountSortCode() {
        Integer fromAccountSortCode = transaction.getFromAccountSortCode();
        assertEquals(1234, fromAccountSortCode);
    }

    @Test
    void setFromAccountSortCode() {
        Integer fromAccountSortCode = transaction.getFromAccountSortCode();
        assertEquals(1234, fromAccountSortCode);
    }

    @Test
    void getToAccount() {
        Long toAccount = transaction.getToAccount();
        assertEquals(9876543210L, toAccount);
    }

    @Test
    void setToAccount() {
        Long toAccount = transaction.getToAccount();
        assertEquals(9876543210L, toAccount);
    }

    @Test
    void getToAccountSortCode() {
        Integer toAccountSortCode = transaction.getToAccountSortCode();
        assertEquals(1234, toAccountSortCode);
    }

    @Test
    void setToAccountSortCode() {
        Integer toAccountSortCode = transaction.getToAccountSortCode();
        assertEquals(1234, toAccountSortCode);
    }

    @Test
    void getAmount() {
        Double amount = transaction.getAmount();
        assertEquals(500.00, amount);
    }

    @Test
    void setAmount() {
        Double amount = transaction.getAmount();
        assertEquals(500.00, amount);
    }
}
