package com.example.mygarden.testUtil;

import com.example.mygarden.model.entity.Picture;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class TestData {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(long id, String name, BigDecimal price, Set<Picture> pictureSet){
        Product testProduct = new Product();
        testProduct.setId(id);
        testProduct.setName(name);
        testProduct.setPrice(price);
        testProduct.setPictures(pictureSet);
        productRepository.save(testProduct);

        return testProduct;

    }

    public void cleanAllTestData(){
        productRepository.deleteAll();
    }
}
