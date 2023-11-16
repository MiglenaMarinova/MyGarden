package com.example.mygarden.repository;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

        Optional<Role> findRoleByName(RoleEnum name);
}
