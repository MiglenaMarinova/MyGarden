package com.example.mygarden.web;

import com.example.mygarden.service.UserService;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AdminControllerTest {

    public static final String ADMIN_EMAIL = "admin@test.com";
    public static final String USER_EMAIL = "user@test.com";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserTestData userTestData;


    @BeforeEach
    void setUp() {

        userTestData.cleanAllTestData();

    }


    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void adminPageTest() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);
        mockMvc.perform(get("/admin/manage"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-page"));
    }

    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void manageUserTest() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);
        mockMvc.perform(get("/admin/manage/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-page"));
    }

    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void manageProductTest() throws Exception {
        userTestData.createTestAdmin(ADMIN_EMAIL);
        mockMvc.perform(get("/admin/manage/products"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/all"))
                .andExpect(redirectedUrl("/products/all"));
    }

    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void adminPageShouldReturnError() throws Exception {
        userTestData.createTestUser(USER_EMAIL);
        mockMvc.perform(get("/admin/manage"))
                .andExpect(status().is4xxClientError());

    }
}