package com.example.mygarden.service;

import com.example.mygarden.model.dto.UserRegisterDto;
import com.example.mygarden.model.entity.User;

public interface UserService {

    void init();

    void registerUser(UserRegisterDto userRegisterDto);

    User findByEmail(String username);

    void save(User userBuyer);
}
