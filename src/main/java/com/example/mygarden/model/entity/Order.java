package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "orders_products",
           joinColumns =@JoinColumn(name = "order_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> orderedProducts;
    @ManyToOne
    private User placedBy;

    private boolean isPlaced;

    public Order() {
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public Order setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
        return this;
    }

    public User getPlacedBy() {
        return placedBy;
    }

    public Order setPlacedBy(User placedBy) {
        this.placedBy = placedBy;
        return this;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public Order setPlaced(boolean placed) {
        isPlaced = placed;
        return this;
    }
}
