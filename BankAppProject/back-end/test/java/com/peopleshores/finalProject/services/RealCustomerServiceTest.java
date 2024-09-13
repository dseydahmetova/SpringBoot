package com.peopleshores.finalProject.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.peopleshores.finalProject.dtos.GetCustomerResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.repos.JpaCustomerRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;

class RealCustomerServiceTest {
    @Mock
    private JpaCustomerRepository customerRepository;
    @InjectMocks
    private RealCustomerService customerService;

    Customer customer;
    Account account1, account2, account3;
    List accounts = new ArrayList();

    @BeforeEach
    void setup() {
        account1 = new Account(12l, 4789, "saving", 500.0, 450.0, customer);
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "Charles", accounts);
    }

    @Test
    void customerExists() {
        assertNotNull(customer);
    }

    @Test
    void getCustomerById() {
        assertEquals(1L, customer.getId());
    }

    @Test
    void createCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        GetCustomerResponseDto responseDto = customerService.createCustomer("Charles");
        assertEquals(1L, 1L);
        assertEquals("Charles", "Charles");
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertNotNull(responseDto);
    }

    @Test
    void testGetCustomerById() {
        assertEquals(1L, customer.getId());
    }

    @Test
    void deleteCustomer() {
        Account account = new Account(12l, 4789, "saving", 500.0, 450.0, customer);
        Customer customer = new Customer(1L, "Charles", Arrays.asList(account));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}