package com.peopleshores.finalProject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peopleshores.finalProject.dtos.CreateCustomerRequestDto;
import com.peopleshores.finalProject.dtos.GetCustomerResponseDto;
import com.peopleshores.finalProject.entities.Account;
import com.peopleshores.finalProject.entities.Customer;
import com.peopleshores.finalProject.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@CrossOrigin
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private ObjectMapper objectMapper;
    private Customer customer;

    GetCustomerResponseDto responseDto;
    CreateCustomerRequestDto requestDto;
    Account account1;
    List<Account> accountlist = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        account1 = new Account(12L, 4789, "saving", 100.0, 20.0, customer);
        accountlist.add(account1);
        responseDto = new GetCustomerResponseDto(1L, "Charles", List.of(1L, 35L, 36L, 4L));
        requestDto = new CreateCustomerRequestDto("Charles");
        customer = new Customer(1L, "Charles", accountlist);
    }

    @Test
    void testGetCustomerByID() {
        when(customerService.getCustomerById(1L)).thenReturn(responseDto);

        try {
            mockMvc.perform(get("/customer/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())  // Ensure the status is 200 OK
                    .andExpect(jsonPath("id").value(1))  // Corrected JSON path to match a single object
                    .andExpect(jsonPath("fullName").value("Charles"))
                    .andExpect(jsonPath("accounts").exists());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddNewCustomer() {
        when(customerService.createCustomer(anyString())).thenReturn(responseDto);

        try {
            mockMvc.perform(post("/customer")
                            .content(requestDto.fullName()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.fullName").value("Charles"))
                    .andExpect(jsonPath("$.accounts").exists());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testRemoveCustomer() {
        when(customerService.deleteCustomer(1L)).thenReturn(0.00);

        try {
            mockMvc.perform(delete("/customer/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(0.00));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}