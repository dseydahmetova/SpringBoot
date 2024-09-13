package com.peopleshores.finalProject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BankTest {
    Account account;
    Customer customer;

    @BeforeEach
    void setup() {
        account = new Account(13L, 4789, "checking", 200.0, 100.0, customer);
    }

    @Test
    void getSortCode() {
        assertEquals(4789, account.getSortCode());
    }

    @Test
    void setSortCode() {
        account.setSortCode(4321);
        assertEquals(4321, account.getSortCode());
    }
}