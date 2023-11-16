package com.example.mygarden.service;

import com.example.mygarden.model.dto.ProductAddDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.CategoryRepository;
import com.example.mygarden.repository.PictureRepository;
import com.example.mygarden.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PictureRepository pictureRepository;


    public ProductService(ModelMapper modelMapper, ProductRepository productRepository,
                          CategoryRepository categoryRepository, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureRepository = pictureRepository;
    }

    public void addProduct(ProductAddDto productAddDto) {


        Product product = modelMapper.map(productAddDto, Product.class);
        Category category = categoryRepository.findByName(productAddDto.getCategory()).orElse(null);
        if (category != null) {
            product.setCategory(category);
        }

        productRepository.save(product);
    }


    public List<ProductViewDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper
                        .map(product, ProductViewDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductViewDto> findByCategoryName(CategoryEnum categoryEnum) {
        return productRepository.findAllByCategory_Name(categoryEnum)
                .stream()
                .map(product -> modelMapper
                        .map(product, ProductViewDto.class))
                .collect(Collectors.toList());

    }

    public List<ProductViewDto> findByCategoryOther(CategoryEnum categoryEnum) {

        return productRepository.findAllByCategory_Name(categoryEnum)
                .stream()
                .map(product -> modelMapper
                        .map(product, ProductViewDto.class))
                .collect(Collectors.toList());
    }


}
