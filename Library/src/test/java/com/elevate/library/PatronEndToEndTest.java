package com.elevate.library;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@Import(PatronController.class)
@WebMvcTest(PatronController.class)
@ContextConfiguration(locations="classpath:test-beans.xml")
public class PatronEndToEndTest {

    @Autowired
    private PatronController patronController;

    @Test
    void contextLoads() throws Exception{
        assertThat(patronController).isNotNull();
    }
}
