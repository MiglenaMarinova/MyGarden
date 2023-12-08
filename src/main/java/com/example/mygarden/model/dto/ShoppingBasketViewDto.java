package com.example.mygarden.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketViewDto {

    private Long id;

    private BigDecimal totalSum;

    private List<ShoppingItemViewDto> itemList = new ArrayList<>();


    public ShoppingBasketViewDto() {
    }

    public Long getId() {
        return id;
    }

    public ShoppingBasketViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public ShoppingBasketViewDto setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    public List<ShoppingItemViewDto> getItemList() {
        return itemList;
    }

    public ShoppingBasketViewDto setItemList(List<ShoppingItemViewDto> itemList) {
        this.itemList = itemList;
        return this;
    }
}
