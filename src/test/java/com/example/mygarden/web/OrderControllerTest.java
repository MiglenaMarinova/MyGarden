package com.example.mygarden.web;

import com.example.mygarden.model.entity.Order;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.impl.AppUserDetailsServiceImpl;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private OrderService orderService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;

    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void delete() throws Exception {
        userTestData.createTestUser(USER_EMAIL);
        long id = 1L;
        Product testProduct = testData.createProduct(1L, "Name", BigDecimal.valueOf(2.00), new HashSet<>());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/orders/delete/{id}", id)
                                .principal(() -> USER_EMAIL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/orders"))
                .andExpect(view().name("redirect:/user/orders"));


    }

    @Test
    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
    void placeOrderTest() throws Exception {
        User buyer = userTestData.createTestUser(USER_EMAIL);
        List<Product> orderedProducts = new ArrayList<>();
        Order order = testData.creatOrder(1L, buyer, orderedProducts, false);
        Long id = order.getId();

        when(orderRepository.findByPlacedBy(buyer.getId())).thenReturn((order));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders/place-order/{id}", id)
                                .principal(() -> USER_EMAIL))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/orders"))
                .andExpect(view().name("redirect:/user/orders"));



    }
}