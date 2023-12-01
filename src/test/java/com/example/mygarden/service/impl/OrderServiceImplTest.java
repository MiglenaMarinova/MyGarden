package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Order;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.UserService;
import com.example.mygarden.testUtil.TestData;
import com.example.mygarden.testUtil.UserTestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ProductRepository productRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {

        orderService = new OrderServiceImpl(orderRepository,
                userService, modelMapper, productRepository);
    }

    @Test
    void findByUserShouldReturnOrderWhenUserHasOneTest() {

        User buyer = new User();
        buyer.setFirstName("User");
        buyer.setLastName("Userov");
        buyer.setPassword("123");
        buyer.setEmail("user@user.com");
        buyer.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setOrderedProducts(orderedProducts);
        order.setPlacedBy(buyer);
        order.setPlaced(false);

        when(orderRepository.findByPlacedBy(buyer.getId())).thenReturn(order);

        Order existingOrder = orderService.findByUser(buyer.getId());

        Assertions.assertEquals(existingOrder.getPlacedBy().getId(), buyer.getId());
        verify(orderRepository, times(1)).findByPlacedBy(buyer.getId());

    }

    @Test
    void findByUserShouldReturnNullWhenUserHasNoOneTest() {
        User buyer = new User();
        buyer.setId(1L);
        buyer.setFirstName("User");
        buyer.setLastName("Userov");
        buyer.setPassword("123");
        buyer.setEmail("user@user.com");
        buyer.setAddress("Userova 22");

        User buyer1 = new User();
        buyer1.setId(2L);
        buyer1.setFirstName("User1");
        buyer1.setLastName("Userov1");
        buyer1.setPassword("123");
        buyer1.setEmail("user1@user1.com");
        buyer1.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setOrderedProducts(orderedProducts);
        order.setPlacedBy(buyer1);
        order.setPlaced(false);

        when(orderRepository.findByPlacedBy(buyer.getId())).thenReturn(null);

        Order nonExistingOrder = orderService.findByUser(buyer.getId());

        Assertions.assertNull(nonExistingOrder);
        verify(orderRepository, times(1)).findByPlacedBy(anyLong());

    }


    @Test
    void shouldSaveOrder() {

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User1");
        user2.setLastName("Userov1");
        user2.setPassword("123");
        user2.setEmail("user1@user1.com");
        user2.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setOrderedProducts(orderedProducts);
        order.setPlacedBy(user2);
        order.setPlaced(false);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order savedOrder = orderRepository.save(order);

        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(savedOrder.getPlacedBy(), user2);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findAllOpenOrdersByUserTest() {

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User1");
        user2.setLastName("Userov1");
        user2.setPassword("123");
        user2.setEmail("user1@user1.com");
        user2.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderedProducts(orderedProducts);
        order1.setPlacedBy(user2);
        order1.setPlaced(false);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderedProducts(orderedProducts);
        order2.setPlacedBy(user2);
        order2.setPlaced(false);

        when(orderRepository.findAllOpenOrdersByUser(user2.getId())).thenReturn(List.of(order1, order2));

        List<Order> result = orderRepository.findAllOpenOrdersByUser(user2.getId());

        Assertions.assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAllOpenOrdersByUser(user2.getId());

    }

    @Test
    void findAllPlacedOrdersByUserTest() {

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User1");
        user2.setLastName("Userov1");
        user2.setPassword("123");
        user2.setEmail("user1@user1.com");
        user2.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderedProducts(orderedProducts);
        order1.setPlacedBy(user2);
        order1.setPlaced(true);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderedProducts(orderedProducts);
        order2.setPlacedBy(user2);
        order2.setPlaced(false);

        when(orderRepository.findAllPlacedOrdersByUser(user2.getId())).thenReturn(List.of(order1));

        List<Order> result = orderRepository.findAllPlacedOrdersByUser(user2.getId());

        Assertions.assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAllPlacedOrdersByUser(user2.getId());

    }

    @Test
    void placeOrder() {

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User1");
        user2.setLastName("Userov1");
        user2.setPassword("123");
        user2.setEmail("user1@user1.com");
        user2.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderedProducts(orderedProducts);
        order1.setPlacedBy(user2);
        order1.setPlaced(false);

        when(orderRepository.findByPlacedBy(user2.getId())).thenReturn(order1);
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        Order beforePlace= orderRepository.findByPlacedBy(user2.getId());
        beforePlace.setPlaced(true);
        Order afterPlace= orderRepository.save(beforePlace);


        Assertions.assertEquals(true, afterPlace.isPlaced());
        verify(orderRepository, times(1)).findByPlacedBy(user2.getId());
        verify(orderRepository, times(1)).save(beforePlace);


    }

    @Test
    void findByPlacedByTest() {
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User1");
        user2.setLastName("Userov1");
        user2.setPassword("123");
        user2.setEmail("user1@user1.com");
        user2.setAddress("Userova 22");

        List<Product> orderedProducts = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderedProducts(orderedProducts);
        order1.setPlacedBy(user2);
        order1.setPlaced(false);

        when(orderRepository.findByPlacedBy(user2.getId())).thenReturn(order1);

        Order placedOrder = orderService.findByPlacedBy(user2.getId());

        Assertions.assertNotNull(placedOrder);


    }


}