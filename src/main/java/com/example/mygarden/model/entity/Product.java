package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Category category;

    private BigDecimal price;
    private Integer amount;
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Picture> pictures;
    @ManyToMany(mappedBy = "orderedProducts", fetch = FetchType.EAGER)
    private List<Order> orders;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public Product setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public Product setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Product setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }
}
