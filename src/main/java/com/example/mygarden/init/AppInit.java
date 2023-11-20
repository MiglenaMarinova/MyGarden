package com.example.mygarden.init;

import com.example.mygarden.service.impl.CategoryService;
import com.example.mygarden.service.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {


    private final UserService userService;
    private final CategoryService categoryService;

    public AppInit(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {

        userService.init();
        categoryService.initCategories();


    }
}
