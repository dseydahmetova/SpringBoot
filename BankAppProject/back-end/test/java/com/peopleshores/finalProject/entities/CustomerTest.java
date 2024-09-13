package com.peopleshores.finalProject.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CustomerTest {
    Customer customer;
    Account account1, account2, account3;
    List accounts = new ArrayList();

    @BeforeEach
    void setup() {
        account1 = new Account(12L, 4789, "saving", 500.0, 450.0, customer);
        account2 = new Account(13L, 4789, "checking", 200.0, 100.0, customer);
        accounts.add(account1);
        accounts.add(account2);
        customer = new Customer(1L, "Dana", accounts);
    }

    @Test
    void getId() {
        assertEquals(1L, customer.getId());
    }

    @Test
    void setId() {
        customer.setId(2L);
        assertEquals(2L, customer.getId());
    }

    @Test
    void getFullName() {
        assertEquals("Dana", customer.getFullName());
    }

    @Test
    void setFullName() {
        customer.setFullName("Dana Seid");
        assertEquals("Dana Seid", customer.getFullName());
    }

    @Test
    void getAccounts() {
        assertEquals(accounts, customer.getAccounts());
    }

    @Test
    void setAccounts() {
        account3 = new Account(14L, 4789, "checking", 300.0, 100.0, customer);
        accounts.add(account3);
        assertEquals(account3, customer.getAccounts().get(2));
    }

    @Test
    void customerExists() {
        assertNotNull(account1);
    }
}
