package com.example.mygarden.service;

import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.ShoppingBasket;
import org.springframework.security.core.userdetails.UserDetails;

public interface ShoppingBasketService {
    void save(ShoppingBasket shoppingBasket);

    void buy(Long id, UserDetails buyer, ProductViewDto productViewDto);



}
