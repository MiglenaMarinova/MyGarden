package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.OrderViewDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.*;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.repository.ShoppingBasketRepository;
import com.example.mygarden.repository.ShoppingItemRepository;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Mock
    private  ShoppingItemRepository shoppingItemRepository;
    @Mock
    private  ShoppingBasketRepository shoppingBasketRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {

        orderService = new OrderServiceImpl(orderRepository,
                userService, modelMapper, shoppingItemRepository, shoppingBasketRepository,productRepository);
    }

    @Test
    void findByUserShouldReturnOrderWhenUserHasOneTest() {

        User buyer = new User();
        buyer.setFirstName("User");
        buyer.setLastName("Userov");
        buyer.setPassword("123");
        buyer.setEmail("user@user.com");
        buyer.setAddress("Userova 22");

        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setShoppingBaskets(shoppingBasketList);
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

        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setShoppingBaskets(shoppingBasketList);
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

        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order = new Order();
        order.setId(1L);
        order.setShoppingBaskets(shoppingBasketList);
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

        String email = "testEmail@test.com";

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(userRole));
        user.setPassword("testPassword");
        when(userService.findByEmail(email)).thenReturn(user);
        User buyer = userService.findByEmail(email);
        long id = buyer.getId();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(OrderServiceImplTest::map)
                        .collect(Collectors.toList()))
                .build();


        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setShoppingBaskets(shoppingBasketList);
        order1.setPlacedBy(user);
        order1.setPlaced(false);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setShoppingBaskets(shoppingBasketList);
        order2.setPlacedBy(user);
        order2.setPlaced(false);

        when(orderRepository.findAllOpenOrdersByUser(user.getId())).thenReturn(List.of(order1, order2));
        when(modelMapper.map(order1, OrderViewDto.class)).thenReturn(new OrderViewDto());
        when(modelMapper.map(order2, OrderViewDto.class)).thenReturn(new OrderViewDto());

        List<Order> result = orderRepository.findAllOpenOrdersByUser(user.getId());
        List<OrderViewDto> list = orderService.findAllOpenOrdersByUser(userDetails);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(result.size(), list.size());
        verify(orderRepository, atLeastOnce()).findAllOpenOrdersByUser(user.getId());

    }

    @Test
    void findAllPlacedOrdersByUserTest() {

        String email = "testEmail@test.com";

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(userRole));
        user.setPassword("testPassword");
        when(userService.findByEmail(email)).thenReturn(user);
        User buyer = userService.findByEmail(email);
        long id = buyer.getId();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(OrderServiceImplTest::map)
                        .collect(Collectors.toList()))
                .build();


        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setShoppingBaskets(shoppingBasketList);
        order1.setPlacedBy(user);
        order1.setPlaced(true);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setShoppingBaskets(shoppingBasketList);
        order2.setPlacedBy(user);
        order2.setPlaced(false);

        when(orderRepository.findAllPlacedOrdersByUser(user.getId())).thenReturn(List.of(order1));
        when(modelMapper.map(order1, OrderViewDto.class)).thenReturn(new OrderViewDto());
//        when(modelMapper.map(order2, ProductViewDto.class)).thenReturn(new ProductViewDto());

        List<Order> result = orderRepository.findAllPlacedOrdersByUser(user.getId());
        List<OrderViewDto> orderViewDtoList = orderService.findAllPlacedOrdersByUser(userDetails);

        Assertions.assertEquals(1, result.size());
        verify(orderRepository, atLeastOnce()).findAllPlacedOrdersByUser(user.getId());

    }

    @Test
    void placeOrder() {

        String email = "testEmail@test.com";

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(userRole));
        user.setPassword("testPassword");
        when(userService.findByEmail(email)).thenReturn(user);
        User buyer = userService.findByEmail(email);
        long id = buyer.getId();

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(OrderServiceImplTest::map)
                        .collect(Collectors.toList()))
                .build();

        Product product = new Product();
        product.setId(1L);
        product.setAmount(10);
        product.setPrice(BigDecimal.valueOf(2.00));
        product.setName("TestProduct");



        Set<ShoppingItem> items = new HashSet<>();
        ShoppingItem item = new ShoppingItem();
        item.setId(1L);
        item.setProduct(product);
        items.add(item);

        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setId(1L);
        shoppingBasket.setShoppingItems(items);


        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();


        Order order1 = new Order();
        order1.setId(1L);
        order1.setShoppingBaskets(shoppingBasketList);
        order1.setPlacedBy(user);
        order1.setPlaced(false);

        when(orderRepository.findByPlacedBy(user.getId())).thenReturn(order1);
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(shoppingBasketRepository.findByOrder(order1.getId())).thenReturn(Optional.of(shoppingBasket));

        orderService.placeOrder(order1.getId(), userDetails);

        Assertions.assertEquals(true, order1.isPlaced());
        verify(orderRepository, times(1)).findByPlacedBy(user.getId());
        verify(orderRepository, times(1)).save(order1);

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

        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();

        Order order1 = new Order();
        order1.setId(1L);
        order1.setShoppingBaskets(shoppingBasketList);
        order1.setPlacedBy(user2);
        order1.setPlaced(false);

        when(orderRepository.findByPlacedBy(user2.getId())).thenReturn(order1);

        Order placedOrder = orderService.findByPlacedBy(user2.getId());

        Assertions.assertNotNull(placedOrder);
        verify(orderRepository, times(1)).findByPlacedBy(user2.getId());


    }


    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}