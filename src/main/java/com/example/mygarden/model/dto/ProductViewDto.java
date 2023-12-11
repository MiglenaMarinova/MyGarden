package com.example.mygarden.model.dto;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductViewDto {

    private Long id;
    private String name;
    private BigDecimal price;

    private Integer amount;

    private List<PictureViewDto> pictureViewList = new ArrayList<>();


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

    public List<PictureViewDto> getPictureViewList() {
        return pictureViewList;
    }

    public void setPictureViewList(List<PictureViewDto> pictureViewList) {
        this.pictureViewList = pictureViewList;
    }

    public Integer getAmount() {
        return amount;
    }

    public ProductViewDto setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
