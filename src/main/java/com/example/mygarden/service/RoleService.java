package com.example.mygarden.service;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.RoleRepository;

import java.util.Optional;

public interface RoleService {



    Role findRoleByName(RoleEnum name);
}
