package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class ShoppingBasket extends BaseEntity{
    @ManyToOne
    private User buyer;
    @OneToMany(mappedBy = "shoppingBasket", fetch = FetchType.EAGER )
    private Set<ShoppingItem> shoppingItems = new HashSet<>();

    private BigDecimal totalSum;
    @ManyToOne
    private Order order;

    public ShoppingBasket() {

    }

    public User getBuyer() {
        return buyer;
    }

    public ShoppingBasket setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Set<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public ShoppingBasket setShoppingItems(Set<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        return this;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public ShoppingBasket setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public ShoppingBasket setOrder(Order order) {
        this.order = order;
        return this;
    }
}
