package com.peopleshores.finalProject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AccountTest {
    Customer customer;
    Account account1, account2, account3;
    List accounts = new ArrayList();

    @BeforeEach
    void setUp() {
        account1 = new Account(12L, 4789, "saving", 500.0, 450.0, customer);
        accounts.add(account1);
        customer = new Customer(1L, "Jane", accounts);
    }

    @Test
    void getNumber() {
        assertEquals(12L, account1.getNumber());
    }

    @Test
    void setNumber() {
        account1.setNumber(14L);
        assertEquals(14, account1.getNumber());
    }

    @Test
    void getSortCode() {
        assertEquals(4789, account1.getSortCode());
    }

    @Test
    void setSortCode() {
        account1.setSortCode(1234);
        assertEquals(1234, account1.getSortCode());
    }

    @Test
    void getName() {
        assertEquals("saving", account1.getName());
    }

    @Test
    void setName() {
        account1.setName("saving 1");
        assertEquals("saving 1", account1.getName());
    }

    @Test
    void getOpeningBalance() {
        assertEquals(500.0, account1.getOpeningBalance());
    }

    @Test
    void setOpeningBalance() {
        account1.setOpeningBalance(200.0);
        assertEquals(200.0, account1.getOpeningBalance());
    }

    @Test
    void getBalance() {
        assertEquals(450.0, account1.getBalance());
    }

    @Test
    void setBalance() {
        account1.setBalance(100.0);
        assertEquals(100.0, account1.getBalance());
    }

    @Test
    void getCustomer() {
        assertEquals(1L, customer.getId());
    }

    @Test
    void setCustomer() {
        customer.setId(5L);
        assertEquals(5L, customer.getId());
    }
}
