package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.dtos.*;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public GetCustomerResponseDto getCustomer(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public GetCustomerResponseDto addNewCustomer(@RequestBody String request) {
        return customerService.createCustomer(request);
    }

//    @PostMapping("/customer")
//    public GetCustomerResponseDto addNewCustomer(@RequestBody CreateCustomerRequestDto request) {
//        return customerService.createCustomer(request.fullName());
//    }

    @DeleteMapping("/{id}")
    public Double removeCustomer(@PathVariable("id") Long id) {
        return customerService.deleteCustomer(id);
    }
}

