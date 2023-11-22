package com.example.mygarden.service;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ProductService {

    void addProduct(ProductAddDto productAddDto);

    List<ProductViewDto> findAll();

    void changePic(Long id);

    void buy(Long id, UserDetails buyer);

    void delete(Long id);


//    void delete(Long id, UserDetails buyer);
}
