package com.elevate.library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//reference
//https://stackoverflow.com/questions/55269036/spring-mockmvc-match-a-collection-of-json-objects-in-any-order

@ExtendWith(SpringExtension.class)
@Import(LoanController.class)
@WebMvcTest(LoanController.class)
@ContextConfiguration(locations="classpath:test-beans.xml")
public class LoanEndpointsTest {
    Book returnableBook, returnableBook1;
    Patron returnablePatron, returnablePatron1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronDAO patrons;

    @MockBean
    private BookDAO books;

    @MockBean
    private LoanDAO loans;

    @BeforeEach
    void setup() {
        returnableBook = new Book(5, "Java Patterns", "Gang of 4");
        returnableBook1 = new Book(3, "Rising Strong", "Brene Brown");
        returnablePatron = new Patron(10, "Charlie");
        returnablePatron1 = new Patron(8, "Mark");
    }

    @Test
    void getAllLoans() {
        Loan loan1 = new Loan(1, returnableBook.getId(), returnablePatron.getId());
        Loan loan2 = new Loan(2, returnableBook1.getId(), returnablePatron1.getId(), true);
        when(loans.getAllLoans()).thenReturn(List.of(loan1, loan2));

        try {
            mockMvc.perform(get("/loans"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(Collection.class)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id")
                            .value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookid")
                            .value(5))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].patronid")
                            .value(10))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].returned")
                            .value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id")
                            .value(2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookid")
                            .value(3))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].patronid")
                            .value(8))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].returned")
                            .value(true))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void getLoanByID() {
        when(loans.getById(21)).thenReturn(new Loan(21, returnableBook.getId(), returnablePatron.getId()));

        try {
            mockMvc.perform(get("/loans/21"))
                    .andExpect(MockMvcResultMatchers.jsonPath("bookid")
                            .value(5))
                    .andExpect(MockMvcResultMatchers.jsonPath("patronid")
                            .value(10))
                    .andExpect(MockMvcResultMatchers.jsonPath("returned")
                            .value(false))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void makeNewLoan() {
        when(books.getById(5)).thenReturn(returnableBook);
        when(patrons.getById(10)).thenReturn(returnablePatron);
        Loan lOutput = new Loan(21, returnableBook.getId(), returnablePatron.getId(), false);
        when(loans.addLoan(any(Loan.class))).thenReturn(lOutput);

        try {
            mockMvc.perform(post("/loans")
                            .content("""
                                {"bookid": 5,"patronid": 10}
                                """)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("bookid").value(5))
                    .andExpect(MockMvcResultMatchers.jsonPath("patronid").value(10))
                    .andExpect(MockMvcResultMatchers.jsonPath("returned").value(false));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateLoan() {
        Loan lIntput = new Loan(21, returnableBook.getId(), returnablePatron.getId(), false);
        Loan lOutput = new Loan(21, returnableBook1.getId(), returnablePatron1.getId(), true);

        when(loans.getById(21)).thenReturn(lIntput);
        when(loans.updateLoan(lIntput)).thenReturn(lOutput);

        try {
            mockMvc.perform(put("/loans/21/return")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("bookid").value(3))
                    .andExpect(MockMvcResultMatchers.jsonPath("patronid").value(8))
                    .andExpect(MockMvcResultMatchers.jsonPath("returned").value(true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

