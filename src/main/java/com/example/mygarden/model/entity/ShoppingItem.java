package com.example.mygarden.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "shopping_items")
public class ShoppingItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "shopping_basket_id", referencedColumnName = "id")
    private ShoppingBasket shoppingBasket;


    private String name;

    private Integer amount;

    private BigDecimal totalPrice;

    public ShoppingItem() {
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public ShoppingItem setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
        return this;
    }


    public Integer getAmount() {
        return amount;
    }

    public ShoppingItem setAmount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public ShoppingItem setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShoppingItem setName(String name) {
        this.name = name;
        return this;
    }
}
