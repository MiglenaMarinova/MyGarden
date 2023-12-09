package com.example.mygarden.service;

import com.example.mygarden.model.entity.ShoppingItem;

public interface ShoppingItemService {
    void save(ShoppingItem shoppingItem);


    ShoppingItem findByProductAndBasket(Long id, Long id1);
}
