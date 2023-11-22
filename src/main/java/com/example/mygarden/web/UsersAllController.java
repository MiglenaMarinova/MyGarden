package com.example.mygarden.web;

import com.example.mygarden.model.dto.UserViewDto;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersAllController {

    private final UserService userService;

    public UsersAllController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String all(Model model) {

        List<UserViewDto> allUsers = userService.findAllUsers();
        model.addAttribute("allUsers", allUsers);

        List<UserViewDto> allModerators = userService.findAllModerators();
        model.addAttribute("allModerators", allModerators);

        List<UserViewDto> allAdmins = userService.findAllAdmins();
        model.addAttribute("allAdmins", allAdmins);

        return "users";
    }

    @GetMapping("make-admin/{id}")
    public String makeAdmin(@PathVariable Long id) {

        this.userService.makeAdmin(id);

        return "redirect:/users/all";
    }

    @GetMapping("make-moderator/{id}")
    public String makeModerator(@PathVariable Long id) {

        this.userService.makeModerator(id);

        return "redirect:/users/all";
    }

    @GetMapping("remove-moderator/{id}")
    public String removeModerator(@PathVariable Long id) {

        this.userService.removeModerator(id);

        return "redirect:/users/all";


    }
}