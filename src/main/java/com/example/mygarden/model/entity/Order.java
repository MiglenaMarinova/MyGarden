package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private BigDecimal total;


    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<ShoppingBasket> shoppingBaskets = new ArrayList<>();


    @ManyToOne
    private User placedBy;

    private boolean isPlaced;

    public Order() {
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

     public BigDecimal getTotal() {
        return total;
    }

    public Order setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public List<ShoppingBasket> getShoppingBaskets() {
        return shoppingBaskets;
    }

    public Order setShoppingBaskets(List<ShoppingBasket> shoppingBaskets) {
        this.shoppingBaskets = shoppingBaskets;
        return this;
    }
}
