package com.example.mygarden.service;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Product;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProductService {

    void addProduct(ProductAddDto productAddDto);

    List<ProductViewDto> findAll();

    void changePic(Long id);

    void delete(Long id);

    ProductViewDto findById(Long id);

    void update(ProductViewDto productToUpdate);

    Product findProduct(Long id);

    void saveChanges(Product existingProduct);




//    void delete(Long id, UserDetails buyer);
}
