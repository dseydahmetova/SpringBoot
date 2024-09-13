package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.TransactionRequestDto;
import com.peopleshores.finalProject.dtos.TransactionResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.entities.TransactionType;
import com.peopleshores.finalProject.exceptions.AccountNotFoundException;
import com.peopleshores.finalProject.exceptions.InsufficientBalanceException;
import com.peopleshores.finalProject.repos.JpaAccountRepository;
import com.peopleshores.finalProject.repos.JpaTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class RealTransactionService implements TransactionService {
    private final JpaTransactionRepository transactionRepository;
    private final JpaAccountRepository accountRepository;

    public RealTransactionService(JpaTransactionRepository transactionRepository, JpaAccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransactionResponseDto postTransactionByType(TransactionRequestDto request) throws AccountNotFoundException, InsufficientBalanceException {
        ModelMapper modelMapper = new ModelMapper();
        Transaction transaction = modelMapper.map(request, Transaction.class);
        transaction.setTime(LocalDateTime.now());
        transactionRepository.save(transaction);
        TransactionType type = request.getType();
        Double amount = request.getAmount();

        if (amount > 0) {
            switch (type) {
                case DEPOSIT -> {
                    transfer(transaction.getToAccount(), amount);
                }
                case WITHDRAWAL -> {
                    transfer(transaction.getFromAccount(), -amount);
                }
                case TRANSFER -> {
                    transfer(transaction.getToAccount(), amount);
                    transfer(transaction.getFromAccount(), -amount);
                }
                default -> throw new IllegalArgumentException("Unexpected value: " + type);
            }
        } else {
            throw new IllegalArgumentException("Unexpected value, amount can not be negative or 0: " + amount);
        }
        return new TransactionResponseDto(transaction.getTime(),
                transaction.getType(),
                transaction.getFromAccount(), transaction.getFromAccountSortCode(),
                transaction.getToAccount(), transaction.getToAccountSortCode(),
                transaction.getAmount());
    }

    private void transfer(Long id, Double amount)
            throws InsufficientBalanceException, AccountNotFoundException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Check if the balance has enough amount for withdrawal or transfer balance
            if (account.getBalance() + amount < 0)
                throw new InsufficientBalanceException("Account " + id +
                        " has insufficient balance for this transaction.");

            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        } else {
            throw new AccountNotFoundException("Account " + id + " not found.");
        }
    }
}

