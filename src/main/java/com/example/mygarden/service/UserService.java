package com.example.mygarden.service;

import com.example.mygarden.model.dto.UserRegisterDto;
import com.example.mygarden.model.dto.UserViewDto;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;

import java.util.List;

public interface UserService {

    void init();

    void registerUser(UserRegisterDto userRegisterDto);

    User findByEmail(String username);

    void save(User userBuyer);

    List<UserViewDto> findAllUsers();

    List<UserViewDto> findAllModerators();

    List<UserViewDto> findAllAdmins();
}
