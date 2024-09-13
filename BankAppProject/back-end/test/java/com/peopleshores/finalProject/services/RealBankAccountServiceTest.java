package com.peopleshores.finalProject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

class RealBankAccountServiceTest {
    @InjectMocks
    private RealBankAccountService realBankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(realBankAccountService, "sortCode", 123456);
    }

    @Test
    void getAccountSortCode() {
        int expectedSortCode = 123456;
        int actualSortCode = realBankAccountService.getAccountSortCode();
        assertEquals(expectedSortCode, actualSortCode, "Sort code should match test value");
    }
}