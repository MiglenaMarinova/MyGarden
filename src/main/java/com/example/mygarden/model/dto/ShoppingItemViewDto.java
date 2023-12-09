package com.example.mygarden.model.dto;

import java.math.BigDecimal;

public class ShoppingItemViewDto {

    private Long id;

    private String name;

    private BigDecimal totalPrice;

    private Integer amount;

    public ShoppingItemViewDto() {
    }

    public Long getId() {
        return id;
    }

    public ShoppingItemViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingItemViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingItemViewDto setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public ShoppingItemViewDto setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}
