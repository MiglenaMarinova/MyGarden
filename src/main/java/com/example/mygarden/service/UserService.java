package com.example.mygarden.service;

import com.example.mygarden.model.dto.UserRegisterDto;

public interface UserService {

    void init();

    void registerUser(UserRegisterDto userRegisterDto);
}
