package com.example.mygarden.web;

import com.example.mygarden.model.entity.*;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.impl.AppUserDetailsServiceImpl;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    public static final String USER_EMAIL = "user@test.com";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserTestData userTestData;
    @Autowired
    private TestData testData;

    @BeforeEach
    void setUp(){
        userTestData.cleanAllTestData();
        testData.cleanAllTestData();

    }
    @AfterEach
    void tearDown(){
        userTestData.cleanAllTestData();
        testData.cleanAllTestData();

    }



    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void delete() throws Exception {

        User buyer = userTestData.createTestUser(USER_EMAIL);
        Set<Picture> pic = new HashSet<>();
        Product testProduct = testData.createProduct(1L, "Test", BigDecimal.valueOf(2.0), pic,new ArrayList<>());
        Order order = testData.creatOrder(1L, buyer,List.of(testProduct), false);
        testProduct.setOrders(List.of(order));
        buyer.setOrders(List.of(order));

        Long id = testProduct.getId();



        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/orders/delete/{id}", id)
                                .principal(() -> USER_EMAIL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/orders"))
                .andExpect(view().name("redirect:/user/orders"));


    }

    @Test
    @WithMockUser(username = "tessst@test.com")
    void placeOrderTest() throws Exception {
        User buyer = userTestData.createTestUser("tessst@test.com");
        Set<Picture> pic = new HashSet<>();
        Product testProduct = testData.createProduct(1L, "Test", BigDecimal.valueOf(2.0), pic,new ArrayList<>());
        Order order = testData.creatOrder(1L, buyer,List.of(testProduct), false);
        testProduct.setOrders(List.of(order));
        buyer.setOrders(List.of(order));

        Long id = order.getId();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders/place-order/{id}", id))


                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/orders"))
                .andExpect(view().name("redirect:/user/orders"));



    }


}