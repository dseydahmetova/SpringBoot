package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.CreateAccountRequestDto;
import com.peopleshores.finalProject.dtos.GetAccountResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.exceptions.CustomerNotFoundException;
import com.peopleshores.finalProject.repos.JpaAccountRepository;
import com.peopleshores.finalProject.repos.JpaCustomerRepository;
import com.peopleshores.finalProject.repos.JpaTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RealAccountService implements AccountService {
    private final JpaAccountRepository accountRepository;
    private final JpaCustomerRepository customerRepository;
    private final JpaTransactionRepository transactionRepository;

    @Value("${sortcode}")
    private Integer sortCode;

    public RealAccountService(JpaAccountRepository accountRepository, JpaCustomerRepository customerRepository, JpaTransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<GetAccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(
                a -> new GetAccountResponseDto(
                        a.getNumber(),
                        a.getSortCode(),
                        a.getName(),
                        a.getOpeningBalance(),
                        new ArrayList<>(),
                        a.getBalance(),
                        a.getCustomer().getId()
                )
        ).toList();
    }

    @Override
    public GetAccountResponseDto createAccount(CreateAccountRequestDto request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()
                -> new CustomerNotFoundException("Customer not found"));
        ModelMapper modelMapper = new ModelMapper();
        Account accountEntity = modelMapper.map(request, Account.class);
        accountEntity.setSortCode(sortCode);
        accountEntity.setBalance(request.getOpeningBalance());
        List<Transaction> transactions = getAllTransactionsForAccount(accountEntity.getNumber());
        accountRepository.save(accountEntity);
        return new GetAccountResponseDto(accountEntity.getNumber(), accountEntity.getSortCode(),
                accountEntity.getName(), accountEntity.getOpeningBalance(), transactions,
                accountEntity.getBalance(), accountEntity.getCustomer().getId());
    }


    @Override
    public GetAccountResponseDto getAccountById(Long id) {
        Account accountEntity = accountRepository.findById(id).orElse(null);
        assert accountEntity != null;
        List<Transaction> transactions = getAllTransactionsForAccount(id);
        return new GetAccountResponseDto(accountEntity.getNumber(), accountEntity.getSortCode(),
                accountEntity.getName(), accountEntity.getOpeningBalance(), transactions, accountEntity.getBalance(),
                accountEntity.getCustomer().getId());
    }

    @Override
    public Double deleteAccount(Long id) {
        Account accountEntity = accountRepository.findById(id).orElse(null);
        assert accountEntity != null;
        accountRepository.deleteById(id);
        return accountEntity.getBalance();
    }

    @Override
    public List<Transaction> getAllTransactionsForAccount(Long accountNumber) {
        return transactionRepository.findAllByAccountNumber(accountNumber);
    }
}
