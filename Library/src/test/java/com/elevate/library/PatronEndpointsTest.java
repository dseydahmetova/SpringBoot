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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(PatronController.class)
@WebMvcTest(PatronController.class)
@ContextConfiguration(locations="classpath:test-beans.xml")
public class PatronEndpointsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronDAO patrons;

    @BeforeEach
    void setup() {

    }


    @Test
    void getPatronByID() {
        when(patrons.getById(10)).thenReturn(new Patron(10, "Charlie"));

        try {
            mockMvc.perform(get("/patrons/10"))
                    .andExpect(MockMvcResultMatchers.jsonPath("name")
                    .value("Charlie"))
                    .andExpect(status().isOk()).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void makeNewPatron() {
        Patron pOutput = new Patron(99, "Charlie");
        when(patrons.addPatron(any(Patron.class))).thenReturn(pOutput);
        try {
            mockMvc.perform(post("/patrons")
                    .content("""
                            {"name" : "Charlie"}
                            """)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("id").value(99));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllPatrons() {
        Patron patron1 = new Patron(1,"Mike");
        Patron patron2 = new Patron(2, "Alex");
        when(patrons.getAllPatrons()).thenReturn(List.of(patron1, patron2));

        try {
            mockMvc.perform(get("/patrons"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(Collection.class)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id")
                            .value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                            .value("Mike"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id")
                            .value(2))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].name")
                            .value("Alex"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
