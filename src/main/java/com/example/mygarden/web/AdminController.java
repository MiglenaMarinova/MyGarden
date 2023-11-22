package com.example.mygarden.web;

import com.example.mygarden.model.dto.UserViewDto;
import com.example.mygarden.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/manage")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String adminPage(){
        return "admin-page";
    }

    @GetMapping("/users")
    public String manageUser(Model model){

        return "admin-page";
    }
    @GetMapping("/products")
    public String manageProduct(){

        return "redirect:/products/all";
    }
}
