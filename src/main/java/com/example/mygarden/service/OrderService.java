package com.example.mygarden.service;

import com.example.mygarden.model.dto.OrderViewDto;
import com.example.mygarden.model.entity.Order;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    Order findByUser(Long id);

    void save(Order order);

    List<OrderViewDto> findAllOpenOrdersByUser(UserDetails currentUser);

    List<OrderViewDto> findAllPlacedOrdersByUser(UserDetails currentUser);


//    void delete(Long id, UserDetails buyer);

    boolean isOwner(Order order, UserDetails buyer);

    void placeOrder(Long id, UserDetails buyer);

    Order findByPlacedBy(Long id);

    void delete(Long id, UserDetails buyer);
}
