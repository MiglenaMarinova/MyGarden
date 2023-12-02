package com.example.mygarden.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void loginTest() throws Exception {
        this.mockMvc.perform(get("/users/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    void onFailureTest() throws Exception {
        this.mockMvc.perform(post("/users/login-error")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("email")).
                andExpect(model().attributeExists("badCredentials")).
                andExpect(view().name("login"));

    }
}