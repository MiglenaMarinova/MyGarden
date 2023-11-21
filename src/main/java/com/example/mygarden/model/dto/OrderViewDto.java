package com.example.mygarden.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderViewDto {
    private Long Id;
    private List<ProductViewDto> products = new ArrayList<>();

    private BigDecimal totalSum;

    public OrderViewDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<ProductViewDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewDto> products) {
        this.products = products;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }
}
