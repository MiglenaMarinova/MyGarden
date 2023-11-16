package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @OneToMany
    private List<Product> orderedProducts;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User placedBy;



    public Order() {
    }


    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public User getPlacedBy() {
        return placedBy;
    }

    public void setPlacedBy(User placedBy) {
        this.placedBy = placedBy;
    }
}
