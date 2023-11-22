package com.example.mygarden.init;

import com.example.mygarden.service.CategoryService;
import com.example.mygarden.service.RoleService;
import com.example.mygarden.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {


   private final UserService userService;
   private final CategoryService categoryService;
   private final RoleService roleService;

    public AppInit(UserService userService, CategoryService categoryService, RoleService roleService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {

        userService.init();
        categoryService.initCategories();



    }
}
