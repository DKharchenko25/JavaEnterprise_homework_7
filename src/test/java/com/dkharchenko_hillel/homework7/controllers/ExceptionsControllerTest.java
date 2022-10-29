package com.dkharchenko_hillel.homework7.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExceptionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getErrorPath() throws Exception {
        mockMvc.perform(get("/error")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("Sorry, something went wrong. Possible issues:\n " +
                        "1. Access denied;\n" +
                        "2. Wrong ID was used in input field;\n" +
                        "3. Invalid input data was used in input field."));
    }
}