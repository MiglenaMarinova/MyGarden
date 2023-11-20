package com.example.mygarden.service;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;

import java.util.List;

public interface ProductService {

    void addProduct(ProductAddDto productAddDto);

    List<ProductViewDto> findAll();

    void changePic(Long id);
}
