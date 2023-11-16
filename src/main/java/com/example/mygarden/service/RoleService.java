package com.example.mygarden.service;

import com.example.mygarden.repository.RoleRepository;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }




    }

