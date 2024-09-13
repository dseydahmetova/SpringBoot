package com.peopleshores.finalProject.controller;

import com.peopleshores.finalProject.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @Test
    void getBankSortCode() {
        int expectedSortCode = 123456;
        mockMvc = MockMvcBuilders.standaloneSetup(bankAccountController).build();

        when(bankAccountService.getAccountSortCode()).thenReturn(expectedSortCode);
        try {
            mockMvc.perform(get("/sortCode")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().string(String.valueOf(expectedSortCode)));

            verify(bankAccountService, times(1)).getAccountSortCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}