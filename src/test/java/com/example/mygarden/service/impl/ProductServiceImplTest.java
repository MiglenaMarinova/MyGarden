package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @MockBean
    private ModelMapper modelMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PictureService pictureService;
    @Mock
    private UserService userService;
    @Mock
    private OrderService orderService;


    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {

        productService = new ProductServiceImpl(modelMapper, productRepository,
                categoryRepository, pictureService, userService, orderService);
    }

    @Test
     void shouldAddProduct(){
        Product testProduct = new Product();
//        testProduct.setId(1L);
        testProduct.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct.setPrice(BigDecimal.valueOf(2.00));


        productRepository.save(testProduct);

        verify(productRepository, times(1)).save(testProduct);

    }

    @Test
    void testFindAllProducts(){

        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        when(productRepository.findAll()).thenReturn(List.of(testProduct1));


        List<Product> result = productRepository.findAll();

        Assertions.assertEquals(1 , result.size());

    }

    @Test
    void findByIdTest(){
        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct1));

       Long id = testProduct1.getId();
        Product result = productService.findProduct(id);

        Assertions.assertEquals(testProduct1, result);
    }
    @Test
    void shouldTrowException(){

       Assertions.assertThrows(ObjectNotFoundException.class, ()-> productService.findProduct(3L));
    }




















}
