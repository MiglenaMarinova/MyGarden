package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.entity.*;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.*;

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




    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {

       productService = new ProductServiceImpl(modelMapper, productRepository,
                categoryRepository, pictureService, userService, orderService);
    }

    @Test
     void shouldAddProduct(){
        ProductAddDto addDto = new ProductAddDto();
        addDto.setPrice(BigDecimal.valueOf(2.00));
        addDto.setName("TestName");
        addDto.setCategory(CategoryEnum.OTHER);

        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName(addDto.getName());
        testProduct.setPrice(addDto.getPrice());
        Set<Picture> pictureSet = new HashSet<>();
        testProduct.setPictures(pictureSet);
        Category category = new Category();
        category.setName(addDto.getCategory());


        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product savedProduct = productRepository.save(testProduct);


        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(testProduct.getName(), "TestName");
        verify(productRepository, times(1)).save(testProduct);

    }

    @Test
    public void testFindAllProducts(){

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
    public void findByIdTest(){
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
    public void shouldTrowException(){

       Assertions.assertThrows(ObjectNotFoundException.class, ()-> productService.findProduct(3L));
    }
    @Test
    public void changePicTest(){

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

        Product product = productRepository.findById(id).get();

        Picture picture = new Picture();
        picture.setId(2L);
        picture.setTitle("TestName");
        when(pictureService.findAllByTittle(testProduct1.getName())).thenReturn(List.of(picture));

        List<Picture> result = pictureService.findAllByTittle(picture.getTitle());

        product.getPictures().addAll(result);

        Assertions.assertEquals(result.size(), product.getPictures().size());

    }

    @Test
    void shouldDeleteProduct(){
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










}
