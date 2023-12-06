package com.example.mygarden.web;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    public static final String ADMIN_EMAIL = "admin@test.com";
    public static final String USER_EMAIL = "user@test.com";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void adminPageTest() throws Exception {
        User testAdmin = new User();
        testAdmin.setId(1L);
        testAdmin.setFirstName("Admin");
        testAdmin.setLastName("Adminov");
        testAdmin.setPassword("123");
        testAdmin.setEmail(ADMIN_EMAIL);
        testAdmin.setAddress("Userova 22");
        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        testAdmin.setRoles(
                List.of(adminRole)
        );
        mockMvc.perform(get("/admin/manage"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-page"));
    }

    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void manageUserTest() throws Exception {
        User testAdmin = new User();
        testAdmin.setId(1L);
        testAdmin.setFirstName("Admin");
        testAdmin.setLastName("Adminov");
        testAdmin.setPassword("123");
        testAdmin.setEmail(ADMIN_EMAIL);
        testAdmin.setAddress("Userova 22");
        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        testAdmin.setRoles(
                List.of(adminRole)
        );
        mockMvc.perform(get("/admin/manage/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-page"));
    }
    @Test
    @WithMockUser(username = ADMIN_EMAIL, roles = {"ADMIN"})
    void manageProductTest() throws Exception {
        User testAdmin = new User();
        testAdmin.setId(1L);
        testAdmin.setFirstName("Admin");
        testAdmin.setLastName("Adminov");
        testAdmin.setPassword("123");
        testAdmin.setEmail(ADMIN_EMAIL);
        testAdmin.setAddress("Userova 22");
        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        testAdmin.setRoles(
                List.of(adminRole)
        );
        mockMvc.perform(get("/admin/manage/products"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products/all"))
                .andExpect(redirectedUrl("/products/all"));
    }

    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void adminPageShouldReturnError() throws Exception {
        User testUser = new User();
        testUser.setFirstName("User");
        testUser.setLastName("Userov");
        testUser.setPassword("123");
        testUser.setEmail(USER_EMAIL);
        testUser.setAddress("Userova 22");

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        testUser.setRoles(
                List.of(userRole)
        );
        mockMvc.perform(get("/admin/manage"))
                .andExpect(status().is4xxClientError());

    }








}
