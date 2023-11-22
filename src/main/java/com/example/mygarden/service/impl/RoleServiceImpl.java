package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.RoleRepository;
import com.example.mygarden.service.RoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(RoleEnum name) {
        return roleRepository.findRoleByName(name).orElse(null);
    }
}

