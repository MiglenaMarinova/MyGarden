package com.example.mygarden.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderViewDto {
    private Long Id;

   private List<ShoppingBasketViewDto> basketViewDtoList = new ArrayList<>();

    private BigDecimal total;

    public OrderViewDto() {
    }

    public Long getId() {
        return Id;
    }

    public OrderViewDto setId(Long id) {
        Id = id;
        return this;
    }

    public List<ShoppingBasketViewDto> getBasketViewDtoList() {
        return basketViewDtoList;
    }

    public OrderViewDto setBasketViewDtoList(List<ShoppingBasketViewDto> basketViewDtoList) {
        this.basketViewDtoList = basketViewDtoList;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderViewDto setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
