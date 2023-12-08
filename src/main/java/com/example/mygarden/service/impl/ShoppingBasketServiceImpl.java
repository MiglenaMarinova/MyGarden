package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.*;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.repository.ShoppingBasketRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.ShoppingBasketService;
import com.example.mygarden.service.ShoppingItemService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    private final ShoppingBasketRepository shoppingBasketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ShoppingItemService shoppingItemService;
    private final OrderService orderService;

    public ShoppingBasketServiceImpl(ShoppingBasketRepository shoppingBasketRepository, ProductRepository productRepository, UserService userService, ShoppingItemService shoppingItemService, OrderService orderService) {
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.shoppingItemService = shoppingItemService;
        this.orderService = orderService;
    }


    @Override
    public void save(ShoppingBasket shoppingBasket) {
        shoppingBasketRepository.save(shoppingBasket);
    }

    @Override
    public void buy(Long id, UserDetails buyer) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not available."));

        User userBuyer = userService.findByEmail(buyer.getUsername());

        Order order = orderService.findByPlacedBy(userBuyer.getId());


        if (order == null) {
            order = new Order();
            order.setPlacedBy(userBuyer);
            orderService.save(order);
        }


        List<ShoppingBasket> shoppingBasketList = order.getShoppingBaskets();

        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByOrder(order.getId()).orElse(null);

        if (shoppingBasket == null) {
            shoppingBasket = new ShoppingBasket();
            shoppingBasket.setOrder(order);
            shoppingBasket.setBuyer(userBuyer);
            shoppingBasketList.add(shoppingBasket);
            shoppingBasketRepository.save(shoppingBasket);
        }

        Set<ShoppingItem> shoppingItems = shoppingBasket.getShoppingItems();

        ShoppingItem shoppingItem = new ShoppingItem();

        shoppingItem.setTotalPrice(product.getPrice());
        shoppingItem.setName(product.getName());
        shoppingItem.setShoppingBasket(shoppingBasket);

        shoppingItemService.save(shoppingItem);

        shoppingItems.add(shoppingItem);


        shoppingBasket.setShoppingItems(shoppingItems);

        BigDecimal totalSum = shoppingBasket
                .getShoppingItems()
                .stream()
                .map(ShoppingItem::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        shoppingBasket.setTotalSum(totalSum);

        shoppingBasketRepository.save(shoppingBasket);

        order.setShoppingBaskets(shoppingBasketList);

        BigDecimal total = order
                .getShoppingBaskets()
                .stream()
                .map(ShoppingBasket::getTotalSum)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        order.setTotal(total);

        orderService.save(order);



    }


}

