package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.*;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.repository.UserRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PictureService pictureService;
    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;
    @Mock
    private AppUserDetailsServiceImpl appUserDetailsService;
    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {

        productService = new ProductServiceImpl(modelMapper, productRepository,
                categoryRepository, pictureService, userService, orderService);

        appUserDetailsService = new AppUserDetailsServiceImpl(userRepository);

    }

    @Test
    void shouldAddProduct() {

        ProductAddDto addDto = new ProductAddDto();
        addDto.setPrice(BigDecimal.valueOf(2.00));
        addDto.setName("TestName");
        Category category = new Category();
        when(categoryRepository.findByName(CategoryEnum.OTHER)).thenReturn(Optional.of(category));
        Optional<Category> category1 = categoryRepository.findByName(CategoryEnum.OTHER);
        addDto.setCategory(CategoryEnum.OTHER);

        when(modelMapper.map(addDto, Product.class)).thenReturn(new Product());
        Product testProduct = modelMapper.map(addDto, Product.class);
        testProduct.setCategory(category1.get());

        productService.addProduct(addDto);

        verify(productRepository).save(any());


    }

    @Test
    public void testFindAllProducts() {

        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        when(productRepository.findAll()).thenReturn(List.of(testProduct1));
        when(modelMapper.map(testProduct1, ProductViewDto.class)).thenReturn(new ProductViewDto());

        List<Product> result = productRepository.findAll();
        List<ProductViewDto> productViewDtoList = productService.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(result.size(), productViewDtoList.size());

    }

    @Test
    public void findByIdTest() {
        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        Long id = testProduct1.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(testProduct1));
        when(modelMapper.map(testProduct1, ProductViewDto.class)).thenReturn(new ProductViewDto());

        ProductViewDto resultView = productService.findById(testProduct1.getId());

        verify(productRepository, atLeastOnce()).findById(any());


    }

    @Test
    public void shouldTrowException() {

        Assertions.assertThrows(ObjectNotFoundException.class, () -> productService.findProduct(3L));
    }

    @Test
    public void changePicTest() {

        Long id = 1L;

        Product testProduct1 = new Product();
        testProduct1.setId(id);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        when(productRepository.findById(id)).thenReturn(Optional.of(testProduct1));

        productService.changePic(testProduct1.getId());

        Picture picture = new Picture();
        picture.setId(2L);
        picture.setTitle("TestName");
        when(pictureService.findAllByTittle(testProduct1.getName())).thenReturn(List.of(picture));

        List<Picture> result = pictureService.findAllByTittle(picture.getTitle());
        Assertions.assertTrue(result.contains(picture));
        Assertions.assertNotNull(result);



        testProduct1.getPictures().addAll(result);

        Assertions.assertEquals(result.size(), testProduct1.getPictures().size());
        verify(productRepository).save(any());

    }

    @Test
    void shouldDeleteProduct() {
        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        doNothing().when(productRepository).deleteById(any());

        productService.delete(testProduct1.getId());

        verify(productRepository, times(1)).deleteById(testProduct1.getId());

    }

    @Test
    void shouldSaveChangesTest() {
        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        productService.saveChanges(testProduct1);

        verify(productRepository).save(any());
    }

    @Test
    void shouldUpdateProduct() {
        ProductViewDto viewDto = new ProductViewDto();
        viewDto.setPrice(BigDecimal.valueOf(2.00));
        viewDto.setName("TestName");

        when(modelMapper.map(viewDto, Product.class)).thenReturn(new Product());
        Product testProduct = modelMapper.map(viewDto, Product.class);

        productService.update(viewDto);

        verify(productRepository).save(any());
    }

//    @Test
//    void addProductToExistingOrderTest() {
//        String email = "testEmail@test.com";
//
//        Role userRole = new Role();
//        userRole.setName(RoleEnum.USER);
//        User user = new User();
//        user.setEmail(email);
//        user.setId(1L);
//        user.setRoles(List.of(userRole));
//        user.setPassword("testPassword");
//        when(userService.findByEmail(email)).thenReturn(user);
//        User buyer = userService.findByEmail(email);
//        long id = buyer.getId();
//
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername(email)
//                .password(user.getPassword())
//                .authorities(user.getRoles()
//                        .stream()
//                        .map(ProductServiceImplTest::map)
//                        .collect(Collectors.toList()))
//                .build();
//
//
//        Set<ShoppingItem> shoppingItemSet = new HashSet<>();
//        ShoppingItem testShoppingItem = new ShoppingItem();
//        testShoppingItem.setId(1L);
//        testShoppingItem.setName("Test");
//        testShoppingItem.setTotalPrice(BigDecimal.valueOf(2.0));
//
//        shoppingItemSet.add(testShoppingItem);
//
//        List<ShoppingBasket> shoppingBasketList = new ArrayList<>();
//
//        Order existingOrder = new Order();
//        existingOrder.setId(1L);
//        existingOrder.setPlacedBy(buyer);
//        existingOrder.setShoppingBaskets(shoppingBasketList);
//        existingOrder.setPlaced(false);
//
//        ShoppingBasket shoppingBasket = new ShoppingBasket();
//        shoppingBasket.setId(1L);
//        shoppingBasket.setBuyer(buyer);
//        shoppingBasket.setShoppingItems(shoppingItemSet);
//        shoppingBasket.setTotalSum(BigDecimal.valueOf(2.00));
//        shoppingBasket.setOrder(existingOrder);
//
//        shoppingBasketList.add(shoppingBasket);
//
//        buyer.setOrders(List.of(existingOrder));
//
//        verify(orderService, atLeastOnce()).save(any());
//
//
//    }
//    @Test
//    void shouldCreateNewOrder(){
//
//        String email = "testEmail@test.com";
//
//        Role userRole = new Role();
//        userRole.setName(RoleEnum.USER);
//        List<Order> orders = new ArrayList<>();
//        User user = new User();
//        user.setOrders(orders);
//        user.setEmail(email);
//        user.setId(1L);
//        user.setRoles(List.of(userRole));
//        user.setPassword("testPassword");
//        when(userService.findByEmail(email)).thenReturn(user);
//        User buyer = userService.findByEmail(email);
//        long id = buyer.getId();
//
//        UserDetails userDetails = org.springframework.security.core.userdetails.User
//                .withUsername(email)
//                .password(user.getPassword())
//                .authorities(user.getRoles()
//                        .stream()
//                        .map(ProductServiceImplTest::map)
//                        .collect(Collectors.toList()))
//                .build();
//
//        Order notExistingOrder = new Order();
//
//        List<Product> ordered = new ArrayList<>();
//
//        verify(orderService, atLeastOnce()).save(any());
//        verify(userService).save(any());
//
//    }




    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }


}
