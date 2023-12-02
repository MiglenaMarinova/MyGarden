package com.example.mygarden.web;

import com.example.mygarden.service.CategoryService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.ProductService;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ModeratorControllerTest {
    public static final String MODERATOR_EMAIL = "moderator@test.com";

    @Autowired
    private UserTestData userTestData;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = MODERATOR_EMAIL, roles = {"MODERATOR"})
    void managePicTest() throws Exception {
        userTestData.createTestModerator(MODERATOR_EMAIL);

        this.mockMvc.perform(get("/moderator/manage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allProducts"))
                .andExpect(model().attributeExists("allAvailablePic"))
                .andExpect(view().name("moderator-page"));
    }
}