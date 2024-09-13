package com.peopleshores.finalProject.services;

import com.peopleshores.finalProject.dtos.GetCustomerResponseDto;
import com.peopleshores.finalProject.entities.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    GetCustomerResponseDto createCustomer(String request);

    GetCustomerResponseDto getCustomerById(Long id);

    Double deleteCustomer(Long id);

}
