package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.PictureRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.PictureService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import com.example.mygarden.testUtil.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {
    @MockBean
    private ModelMapper modelMapper;
    @Mock
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private PictureService pictureService;
    @MockBean
    private PictureRepository pictureRepository;



//    @Test
//    void changePic(){
//
//        Set<Picture> pictureSet = new HashSet<>();
//        Product testProduct = new Product();
//        testProduct.setId(1L);
//        testProduct.setName("Name");
//        testProduct.setPrice(BigDecimal.valueOf(2.00));
//        testProduct.setPictures(pictureSet);
//        productRepository.save(testProduct);
//
//        when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
//        when(productRepository.findById(3L)).thenThrow(new ObjectNotFoundException("Not found"));
//
//
//        Picture pictTest = new Picture();
//        pictTest.setId(1L);
//        pictTest.setTitle("Name");
//        pictTest.setProduct(testProduct);
//        pictureRepository.save(pictTest);
//        List<Picture> picList = new ArrayList<>();
//        when(pictureRepository.findAllByTitle(testProduct.getName())).thenReturn(Optional.of(picList));
//        picList.add(pictTest);
//
//        Set<Picture> updateSet = new HashSet<>(picList);
//
//
//        Product updateTestProduct = new Product();
//        updateTestProduct.setId(testProduct.getId());
//        updateTestProduct.setName(testProduct.getName());
//        updateTestProduct.setPrice(testProduct.getPrice());
//        updateTestProduct.setPictures(updateSet);
//
//        productRepository.save(updateTestProduct);
//
//        Assertions.assertEquals(testProduct.getId(), updateTestProduct.getId());
//        Assertions.assertEquals(updateTestProduct.getName(), pictTest.getTitle());
//        Assertions.assertFalse(testProduct.getPictures().size()== updateTestProduct.getPictures().size());
//
//    }


//
//        List<Picture> availablePic = pictureService.findAllByTittle(product.getName());
//
//        if(!availablePic.isEmpty()){
//          for (Picture picture : availablePic){
//              product.getPictures().add(picture);
//              picture.setProduct(product);
//          }
//
//        }
//
//        productRepository.save(product);
}
