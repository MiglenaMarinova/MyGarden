package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.ShoppingItem;
import com.example.mygarden.repository.ShoppingItemRepository;
import com.example.mygarden.service.ShoppingItemService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;

    public ShoppingItemServiceImpl(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @Override
    public void save(ShoppingItem shoppingItem) {
        shoppingItemRepository.save(shoppingItem);
    }






}
