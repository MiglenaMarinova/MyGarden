package com.example.mygarden.web;

import com.example.mygarden.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails buyer){

        this.orderService.delete(id, buyer);

        return "redirect:/user/orders";
    }

    @GetMapping("place-order/{id}")
    public String placeOrder(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails buyer){

        orderService.placeOrder(id, buyer);


        return "redirect:/user/orders";
    }
}
