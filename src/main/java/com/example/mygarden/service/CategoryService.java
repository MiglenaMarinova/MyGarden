package com.example.mygarden.service;

import com.example.mygarden.model.dto.CategoryPicDto;
import com.example.mygarden.model.enums.CategoryEnum;

import java.util.List;

public interface CategoryService {

    void initCategories();

    CategoryPicDto findByNameVeg(CategoryEnum categoryEnum);

    List<CategoryPicDto> findAll();
    CategoryPicDto findByNameOther(CategoryEnum categoryEnum);

    CategoryPicDto findByNameSpices(CategoryEnum categoryEnum);

    CategoryPicDto findByNameHomemade(CategoryEnum categoryEnum);
}
