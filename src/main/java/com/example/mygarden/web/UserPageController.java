package com.example.mygarden.web;

import com.example.mygarden.model.dto.OrderViewDto;
import com.example.mygarden.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserPageController {

    private final OrderService orderService;

    public UserPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String allOrders(Model model, @AuthenticationPrincipal UserDetails currentUser){


        List<OrderViewDto> allOpenOrders = orderService.findAllOpenOrdersByUser(currentUser);
        model.addAttribute("allOpenOrders", allOpenOrders);

        List<OrderViewDto> allPlacedOrders = orderService.findAllPlacedOrdersByUser(currentUser);
        model.addAttribute("allPlacedOrders", allPlacedOrders);

        return "user-page";
    }
}
