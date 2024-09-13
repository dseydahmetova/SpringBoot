package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.dtos.CreateAccountRequestDto;
import com.peopleshores.finalProject.dtos.GetAccountResponseDto;
import com.peopleshores.finalProject.entities.Transaction;
import com.peopleshores.finalProject.entities.TransactionType;
import com.peopleshores.finalProject.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    private List<Transaction> transactions;
    private GetAccountResponseDto responseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        responseDto = new GetAccountResponseDto(45L, 4789, "saving", 100.0, transactions, 100.0, 1L);
    }

    @Test
    void getAllAccounts() {
        when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(responseDto));

        try {
            mockMvc.perform(get("/account"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].number").value(responseDto.number()))
                    .andExpect(jsonPath("$[0].name").value(responseDto.name()))
                    .andExpect(jsonPath("$[0].balance").value(responseDto.balance()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAccountByID() {
        when(accountService.getAccountById(1L)).thenReturn(responseDto);

        try {
            mockMvc.perform(get("/account/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(45))
                    .andExpect(jsonPath("$.name").value("saving"))
                    .andExpect(jsonPath("$.balance").value(100.0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addNewAccount() {
        transactions = List.of(new Transaction(3L, LocalDateTime.now(), TransactionType.TRANSFER, 2L, 2, 3L, 4, 1000.0));

        GetAccountResponseDto responseDto = new GetAccountResponseDto(1L, 98745,
                "Savings Account", 100.0,
                new ArrayList<>(), 100.0, 1L);

        when(accountService.createAccount(ArgumentMatchers.any(CreateAccountRequestDto.class))).thenReturn(responseDto);
        try {
            mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON).content(
                                    """
                                            {
                                                "customerId": 1,
                                                "accountName": "Savings",
                                                "openingBalance": 100.00
                                            }
                                            """
                            ))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.number").value(1))
                    .andExpect(jsonPath("$.sortCode").value(98745))
                    .andExpect(jsonPath("$.name").value("Savings Account"))
                    .andExpect(jsonPath("$.openingBalance").value(100.0))
                    .andExpect(jsonPath("$.transactions").isArray())
                    .andExpect(jsonPath("$.balance").value(100.0))
                    .andExpect(jsonPath("$.customer").value(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void removeAccount() {
        when(accountService.deleteAccount(1L)).thenReturn(0.0);

        try {
            mockMvc.perform(delete("/account/12345"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(0.0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}