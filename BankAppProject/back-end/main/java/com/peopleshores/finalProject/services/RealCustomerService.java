package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.*;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.exceptions.CustomerNotFoundException;
import com.peopleshores.finalProject.repos.JpaCustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RealCustomerService implements CustomerService {
    private final JpaCustomerRepository customerRepository;

    public RealCustomerService(JpaCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public GetCustomerResponseDto createCustomer(String request) {
        Customer customerEntity = new Customer(request);
        customerRepository.save(customerEntity);
        List<Long> customerAccounts = getCustomerAccountNumbers(customerEntity);
        return new GetCustomerResponseDto(customerEntity.getId(), customerEntity.getFullName(), customerAccounts);
    }

    @Override
    public GetCustomerResponseDto getCustomerById(Long id) {
        Customer customerEntity = customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id " + id + " not found."));
        List<Long> customerAccounts = getCustomerAccountNumbers(customerEntity);
        return new GetCustomerResponseDto(customerEntity.getId(), customerEntity.getFullName(), customerAccounts);
    }

    @Override
    public Double deleteCustomer(Long id) {
        Customer customerEntity = customerRepository.findById(id).orElse(null);
        assert customerEntity != null;
        List<Account> allAccounts = customerEntity.getAccounts();
        double totalBalance = allAccounts.stream()
                .mapToDouble(account -> (double) account.getBalance())
                .sum();
        customerRepository.deleteById(id);
        return totalBalance;
    }

    public List<Long> getCustomerAccountNumbers(Customer customerEntity) {
        List<Account> accounts = customerEntity.getAccounts();
        if (accounts == null || accounts.isEmpty()) {
            return Collections.emptyList();
        }
        return accounts.stream()
                .map(Account::getNumber)
                .collect(Collectors.toList());
    }
}





