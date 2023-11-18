package com.example.mygarden.model.dto;

import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductAddDto {
    @NotNull
    @Size(min = 5, max = 20, message = "Name length must be between 5 and 20")
    private String name;
    @Positive(message = "The price must be a positive number")
    @NotNull(message = "Please enter the price")
    private BigDecimal price;
    @NotNull(message = "Please select a category")
    private CategoryEnum category;




    public ProductAddDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer amount;



    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
