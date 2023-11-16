package com.example.mygarden.model.dto;

import com.example.mygarden.model.enums.CategoryEnum;

import java.util.ArrayList;
import java.util.List;

public class CategoryPicDto {

    private CategoryEnum name;
    private List<ProductViewDto> productViewDtoList;

    public CategoryPicDto() {
    }

    public CategoryPicDto(List<ProductViewDto> productViewDtoList) {
        this.productViewDtoList = new ArrayList<>();
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public List<ProductViewDto> getProductViewDtoList() {
        return productViewDtoList;
    }

    public void setProductViewDtoList(List<ProductViewDto> productViewDtoList) {
        this.productViewDtoList = productViewDtoList;
    }
}
