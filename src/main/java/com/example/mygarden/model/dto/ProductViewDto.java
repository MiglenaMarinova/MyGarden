package com.example.mygarden.model.dto;


import com.example.mygarden.model.entity.Category;
import com.example.mygarden.model.enums.CategoryEnum;

import java.math.BigDecimal;

public class ProductViewDto {

    private Long id;
    private String name;
    private BigDecimal price;

   private String picUrl;


    public ProductViewDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


}
