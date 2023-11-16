package com.example.mygarden.model.entity;

import com.example.mygarden.model.enums.RoleEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role() {
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
