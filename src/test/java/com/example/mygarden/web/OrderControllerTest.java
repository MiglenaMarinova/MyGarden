//package com.example.mygarden.web;
//
//import com.example.mygarden.model.entity.*;
//import com.example.mygarden.testUtil.TestData;
//import com.example.mygarden.testUtil.UserTestData;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//class OrderControllerTest {
//
//    public static final String USER_EMAIL = "user@test.com";
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private UserTestData userTestData;
//    @Autowired
//    private TestData testData;
//
//    @BeforeEach
//    void setUp(){
//        userTestData.cleanAllTestData();
//        testData.cleanAllTestData();
//
//    }
//    @AfterEach
//    void tearDown(){
//        userTestData.cleanAllTestData();
//        testData.cleanAllTestData();
//
//    }
//
//
//
//    @Test
//    @WithMockUser(username = USER_EMAIL, roles = {"USER"})
//    void delete() throws Exception {
//
//        User buyer = userTestData.createTestUser(USER_EMAIL);
//
//        Set<Picture> pictureSet = new HashSet<>();
//        Product product = testData.createProduct(1L, "TestProduct", BigDecimal.valueOf(2.0), pictureSet);
//
//        Set<ShoppingItem> shoppingItemSet = new HashSet<>();
//        ShoppingItem testShoppingItem = testData.createShoppingItem(1L, "Test", BigDecimal.valueOf(2.0),product);
//        shoppingItemSet.add(testShoppingItem);
//        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();
//        Order order = testData.creatOrder(1L, buyer,shoppingBasketList, false);
//
//        ShoppingBasket shoppingBasket = testData.creatShoppingBasket(1L, buyer, shoppingItemSet, BigDecimal.valueOf(2.0), order);
//        shoppingBasketList.add(shoppingBasket);
//
//        buyer.setOrders(List.of(order));
//
//        Long id = testShoppingItem.getId();
//
//
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.delete("/orders/delete/{id}", id)
//                                .principal(() -> USER_EMAIL))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/user/orders"))
//                .andExpect(view().name("redirect:/user/orders"));
//
//
//    }
//
//    @Test
//    @WithMockUser(username = "tessst@test.com")
//    void placeOrderTest() throws Exception {
//        User buyer = userTestData.createTestUser("tessst@test.com");
//
//        Set<Picture> pictureSet = new HashSet<>();
//        Product product = testData.createProduct(1L, "TestProduct", BigDecimal.valueOf(2.0), pictureSet);
//        ShoppingItem testShoppingItem = testData.createShoppingItem(1L, "Test", BigDecimal.valueOf(2.0), product);
//        Set<ShoppingItem> itemSet = new HashSet<>();
//        itemSet.add(testShoppingItem);
//
//        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();
//        Order order = testData.creatOrder(1L, buyer,shoppingBasketList, false);
//        buyer.setOrders(List.of(order));
//
//        ShoppingBasket shoppingBasket = testData.creatShoppingBasket(1L, buyer,itemSet, BigDecimal.valueOf(2.00), order);
//        shoppingBasketList.add(shoppingBasket);
//
//        Long id = order.getId();
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/orders/place-order/{id}", id))
//
//
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/user/orders"))
//                .andExpect(view().name("redirect:/user/orders"));
//
//
//
//    }
//
//
//}