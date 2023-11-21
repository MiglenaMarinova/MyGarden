package com.example.mygarden.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersAllController {

    @GetMapping("/all")
    public String all(){

        return "users";
    }
}
