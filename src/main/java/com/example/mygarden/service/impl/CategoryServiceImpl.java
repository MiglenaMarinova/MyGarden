package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.CategoryPicDto;
import com.example.mygarden.model.dto.PictureViewDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.enums.CategoryEnum;
import com.example.mygarden.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements com.example.mygarden.service.CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public void initCategories() {

        if (this.categoryRepository.count() != 0) {
            return;
        }

        Arrays.stream(CategoryEnum.values())
                .forEach(c -> {
                    Category category = new Category();
                    category.setName(c);
                    category.setDescription(c.getValue());

                    this.categoryRepository.save(category);
                });

    }


    public List<CategoryPicDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryPicDto categoryPicDto = new CategoryPicDto();
                    categoryPicDto.setName(category.getName());
                    List<ProductViewDto> productViewDtoList = category.getProducts()
                            .stream()
                            .map(product -> modelMapper.map(product, ProductViewDto.class))
                            .collect(Collectors.toList());
                    categoryPicDto.setProductViewDtoList(productViewDtoList);
                    return categoryPicDto;
                }).collect(Collectors.toList());
    }

    public CategoryPicDto findByNameOther(CategoryEnum categoryEnum) {
        return getCategoryPicDto(categoryEnum);
    }

    public CategoryPicDto findByNameVeg(CategoryEnum categoryEnum) {
        return getCategoryPicDto(categoryEnum);
    }

    public CategoryPicDto findByNameSpices(CategoryEnum categoryEnum) {

        return getCategoryPicDto(categoryEnum);
    }

    public CategoryPicDto findByNameHomemade(CategoryEnum categoryEnum) {
        return getCategoryPicDto(categoryEnum);

    }

    private CategoryPicDto getCategoryPicDto(CategoryEnum categoryEnum) {
        Category category = categoryRepository.findByName(categoryEnum).orElse(null);
        CategoryPicDto categoryPicDto = new CategoryPicDto();
        if (category != null){
            categoryPicDto.setName(category.getName());
            List<ProductViewDto> products = category.getProducts()
                    .stream()
                    .map(product -> {
                        ProductViewDto productViewDto = modelMapper.map(product, ProductViewDto.class);
                        List<PictureViewDto> pictureViewDtos =
                                product.getPictures()
                                        .stream()
                                        .map(picture -> modelMapper.map(picture, PictureViewDto.class))
                                        .collect(Collectors.toList());
                        productViewDto.setPictureViewList(pictureViewDtos);
                        return productViewDto;
                    }).collect(Collectors.toList());

            categoryPicDto.setProductViewDtoList(products);
        }

        return categoryPicDto;
    }
}
