package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.OrderViewDto;
import com.example.mygarden.model.dto.ProductViewDto;
import com.example.mygarden.model.dto.ShoppingBasketViewDto;
import com.example.mygarden.model.dto.ShoppingItemViewDto;
import com.example.mygarden.model.entity.*;
import com.example.mygarden.repository.OrderRepository;
import com.example.mygarden.repository.ProductRepository;
import com.example.mygarden.repository.ShoppingBasketRepository;
import com.example.mygarden.repository.ShoppingItemRepository;
import com.example.mygarden.service.OrderService;
import com.example.mygarden.service.ShoppingBasketService;
import com.example.mygarden.service.UserService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ShoppingItemRepository shoppingItemRepository;

    private final ShoppingBasketRepository shoppingBasketRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService,
                            ModelMapper modelMapper, ShoppingItemRepository shoppingItemRepository,
                            ShoppingBasketRepository shoppingBasketRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
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
                    List<ShoppingBasketViewDto> basketViewDtoList = order.getShoppingBaskets()
                            .stream()
                            .map(shoppingBasket -> {
                                ShoppingBasketViewDto shoppingBasketViewDto = modelMapper.map(shoppingBasket, ShoppingBasketViewDto.class);
                                List<ShoppingItemViewDto> shoppingItemViewDtoList = shoppingBasket.getShoppingItems()
                                        .stream()
                                        .map(shoppingItem -> {
                                            ShoppingItemViewDto shoppingItemViewDto = modelMapper.map(shoppingItem, ShoppingItemViewDto.class);
                                            shoppingItemViewDto.setName(shoppingItem.getName());
                                            return shoppingItemViewDto;
                                        }).collect(Collectors.toList());

                                shoppingBasketViewDto.setItemList(shoppingItemViewDtoList);
                                return shoppingBasketViewDto;
                            }).collect(Collectors.toList());

                    BigDecimal total = order.getShoppingBaskets()
                            .stream()
                            .map(shoppingBasket -> shoppingBasket.getTotalSum())
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO);
                    orderViewDto.setTotal(total);

                    orderViewDto.setBasketViewDtoList(basketViewDtoList);
                    return orderViewDto;

                }).collect(Collectors.toList());

        return allOpen;

    }

    @Override
    public List<OrderViewDto> findAllPlacedOrdersByUser(UserDetails currentUser) {
        User current = userService.findByEmail(currentUser.getUsername());

        List<OrderViewDto> placedOrders = orderRepository.findAllPlacedOrdersByUser(current.getId())
                .stream()
                .map(order -> {
                    OrderViewDto orderViewDto = modelMapper.map(order, OrderViewDto.class);
                    List<ShoppingBasketViewDto> basketViewDtoList = order.getShoppingBaskets()
                            .stream()
                            .map(shoppingBasket -> {
                                ShoppingBasketViewDto shoppingBasketViewDto = modelMapper.map(shoppingBasket, ShoppingBasketViewDto.class);
                                List<ShoppingItemViewDto> shoppingItemViewDtoList = shoppingBasket.getShoppingItems()
                                        .stream()
                                        .map(shoppingItem -> {
                                            ShoppingItemViewDto shoppingItemViewDto = modelMapper.map(shoppingItem, ShoppingItemViewDto.class);
                                            shoppingItemViewDto.setName(shoppingItem.getName());
                                            return shoppingItemViewDto;
                                        }).collect(Collectors.toList());

                                shoppingBasketViewDto.setItemList(shoppingItemViewDtoList);
                                return shoppingBasketViewDto;
                            }).collect(Collectors.toList());

                    BigDecimal total = order.getShoppingBaskets()
                            .stream()
                            .map(shoppingBasket -> shoppingBasket.getTotalSum())
                            .reduce(BigDecimal::add)
                            .orElse(BigDecimal.ZERO);
                    orderViewDto.setTotal(total);

                    orderViewDto.setBasketViewDtoList(basketViewDtoList);
                    return orderViewDto;

                }).collect(Collectors.toList());

        return placedOrders;
    }



    @Override
    public void placeOrder(Long id, UserDetails buyer) {
        User userBuyer = userService.findByEmail(buyer.getUsername());
        Order order = orderRepository.findByPlacedBy(userBuyer.getId());
        ShoppingBasket shoppingBasket = shoppingBasketRepository.findByOrder(order.getId()).orElse(null);
        assert shoppingBasket != null;
        Set<ShoppingItem> ordered = shoppingBasket.getShoppingItems();
        for (ShoppingItem item : ordered){
            Product product = productRepository.findById(item.getProduct().getId()).get();

            product.setAmount(product.getAmount() - item.getAmount());
            productRepository.save(product);
        }

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
        ShoppingItem toDelete = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found."));
        if (order != null && !order.isPlaced()) {
            ShoppingBasket shoppingBasket = shoppingBasketRepository.findByOrder(order.getId()).orElse(null);
            toDelete.setShoppingBasket(null);

            assert shoppingBasket != null;
            shoppingBasket.getShoppingItems().remove(toDelete);
            shoppingItemRepository.delete(toDelete);
            BigDecimal totalSum = shoppingBasket
                    .getShoppingItems()
                    .stream()
                    .map(ShoppingItem::getTotalPrice)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            shoppingBasket.setTotalSum(totalSum);

            shoppingBasketRepository.save(shoppingBasket);

            order.setTotal(totalSum);
            orderRepository.save(order);




        }
    }

}
