package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.OrderViewDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.entity.Order;
import com.example.mygarden.model.entity.Product;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ModelMapper modelMapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }


    @Override
    public Order findByUser(Long id) {
        return orderRepository.findByPlacedBy(id);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<OrderViewDto> findAllOpenOrdersByUser(UserDetails currentUser) {
        User current = userService.findByEmail(currentUser.getUsername());
        List<OrderViewDto> allOpen = orderRepository.findAllOpenOrdersByUser(current.getId())
                .stream()
                .map(order -> {
                    OrderViewDto orderViewDto = modelMapper.map(order, OrderViewDto.class);
                    List<ProductViewDto> productViewDtoList =
                            order.getOrderedProducts()
                                    .stream()
                                    .map(product -> modelMapper.map(product, ProductViewDto.class))
                                    .collect(Collectors.toList());
                    orderViewDto.setProducts(productViewDtoList);
                    BigDecimal total = order.getOrderedProducts()
                                    .stream()
                                            .map(product -> product.getPrice())
                                                    .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO);
                    orderViewDto.setTotalSum(total);

                    return orderViewDto;
                }).collect(Collectors.toList());

        return allOpen;
    }

    @Override
    public List<OrderViewDto> findAllPlacedOrdersByUser(UserDetails currentUser) {
        User current = userService.findByEmail(currentUser.getUsername());
        List<OrderViewDto> allPlaced = orderRepository.findAllPlacedOrdersByUser(current.getId())
                .stream()
                .map(order -> {
                    OrderViewDto orderViewDto = modelMapper.map(order, OrderViewDto.class);
                    List<ProductViewDto> productViewDtoList =
                            order.getOrderedProducts()
                                    .stream()
                                    .map(product -> modelMapper.map(product, ProductViewDto.class))
                                    .collect(Collectors.toList());
                    orderViewDto.setProducts(productViewDtoList);
                    BigDecimal total = order.getOrderedProducts()
                            .stream()
                            .map(product -> product.getPrice())
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO);
                    orderViewDto.setTotalSum(total);

                    return orderViewDto;
                }).collect(Collectors.toList());

        return allPlaced;
    }

//    @Override
//    @Transactional
//    public void delete(Long id, UserDetails buyer) {
//        User userBuyer = userService.findByEmail(buyer.getUsername());
//        Order order = orderRepository.findByPlacedBy(userBuyer.getId());
//        Product toDelete = productRepository.findById(id)
//                .orElseThrow(() -> new ObjectNotFoundException("Product not available."));
//        if (order != null && !order.isPlaced()) {
//            order.getOrderedProducts().remove(toDelete);
//            orderRepository.save(order);
//        }
//    }



    @Override
    public void placeOrder(Long id, UserDetails buyer) {
        User userBuyer = userService.findByEmail(buyer.getUsername());
        Order order = orderRepository.findByPlacedBy(userBuyer.getId());
        order.setPlaced(true);
        orderRepository.save(order);

    }

    @Override
    public Order findByPlacedBy(Long id) {
        return orderRepository.findByPlacedBy(id);
    }

    @Override
    @Transactional
    public void delete(Long id, UserDetails buyer) {
        User userBuyer = userService.findByEmail(buyer.getUsername());
        Order order = orderRepository.findByPlacedBy(userBuyer.getId());
        Product toDelete = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not available."));
        if (order != null && !order.isPlaced()) {
            toDelete.setOrder(null);
            productRepository.save(toDelete);
            order.getOrderedProducts().remove(toDelete);
            orderRepository.save(order);
        }
    }


}
