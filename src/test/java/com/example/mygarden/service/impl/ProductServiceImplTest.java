package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
   @Mock
    private ProductRepository productRepository;


    @Test
     void shouldReturnProductWhenProductAdd(){
        Product testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct.setPrice(BigDecimal.valueOf(2.00));

        when(productRepository.save(testProduct)).thenReturn(testProduct);

        Product savedProduct = productRepository.save(testProduct);
        Assertions.assertNotNull(savedProduct);


        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testFindAllProducts(){

        List<Product> allProducts = new ArrayList<>();
        Product testProduct1 = new Product();
        testProduct1.setId(1L);
        testProduct1.setName("TestName");
        Set<Picture> pictureSet = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        Product testProduct2 = new Product();
        testProduct2.setId(2L);
        testProduct2.setName("TestName");
        Set<Picture> pictureSet2 = new HashSet<>();
        testProduct1.setPictures(pictureSet);
        Category category1 = new Category();
        category.setName(CategoryEnum.OTHER);
        testProduct1.setPrice(BigDecimal.valueOf(2.00));

        allProducts.add(testProduct1);
        allProducts.add(testProduct2);

        when(productRepository.findAll()).thenReturn(allProducts);

        List<Product> productList = productRepository.findAll();

        Assertions.assertEquals(2, productList.size());
        verify(productRepository, times(1)).findAll();
    }



















}
