
package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.CreateAccountRequestDto;
import com.peopleshores.finalProject.dtos.GetAccountResponseDto;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.repos.JpaAccountRepository;
import com.peopleshores.finalProject.repos.JpaCustomerRepository;
import com.peopleshores.finalProject.repos.JpaTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RealAccountServiceTest {
    @Mock
    private JpaAccountRepository accountRepository;
    @Mock
    private JpaCustomerRepository customerRepository;
    @Mock
    private JpaTransactionRepository transactionRepository;
    @InjectMocks
    private RealAccountService realAccountService;
    @Mock
    private ModelMapper modelMapper;

    Account account1, account2, account3;

    @BeforeEach
    void setUp() {
        account1 = new Account(1L, 123456, "Savings", 1000.0, 1000.0, null);
        account2 = new Account(2L, 654321, "Checking", 1500.0, 1500.0, null);
        account3 = new Account(3L, 123456, "Savings", 1000.0, 950.0, new Customer());
    }

    @Test
    void createAccount() {
        CreateAccountRequestDto requestDto = new CreateAccountRequestDto(1L, "New Account", 500.0);
        Customer customer = new Customer("Jane Doe");
        Account accountEntity = new Account(3L, 789456, "New Account", 500.0, 500.0, customer);
        when(customerRepository.findById(requestDto.getCustomerId())).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(accountEntity);
        GetAccountResponseDto responseDto = realAccountService.createAccount(requestDto);
        assertThat(responseDto.name()).isEqualTo("New Account");
        assertThat(responseDto.openingBalance()).isEqualTo(500.0);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccountById() {
        assertEquals(1L, account1.getNumber());
    }

    @Test
    void deleteAccount() {
        Account account = new Account(1L, 123456, "Savings", 1000.0, 950.0, new Customer());
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Double balance = realAccountService.deleteAccount(1L);
        assertThat(balance).isEqualTo(950.0);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllTransactionsForAccount() {
        List<Transaction> transactions = List.of(new Transaction(1L, null, null, 1L, 123456,
                2L, 654321, 100.0));
        when(transactionRepository.findAllByAccountNumber(1L)).thenReturn(transactions);
        List<Transaction> result = realAccountService.getAllTransactionsForAccount(1L);
        assertThat(result).isEqualTo(transactions);
        verify(transactionRepository, times(1)).findAllByAccountNumber(1L);
    }
}
