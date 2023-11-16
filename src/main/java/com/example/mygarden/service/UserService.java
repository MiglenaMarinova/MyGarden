package com.example.mygarden.service;

import com.example.mygarden.model.dto.UserRegisterDto;
import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.RoleRepository;
import com.example.mygarden.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void init() {

        if (userRepository.count() == 0 && roleRepository.count() == 0){
            Role adminRole = new Role();
            adminRole.setName(RoleEnum.ADMIN);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(RoleEnum.USER);
            roleRepository.save(userRole);

            Role moderatorRole = new Role();
            moderatorRole.setName(RoleEnum.MODERATOR);
            roleRepository.save(moderatorRole);

            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setAddress("Garden 21");
            admin.setEmail("admin@admin.com");
            admin.setEnabled(true);
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRoles(Arrays.asList(adminRole, moderatorRole, userRole));
            userRepository.save(admin);

            User moderator = new User();
            moderator.setFirstName("Moderator");
            moderator.setLastName("Moderator");
            moderator.setAddress("Garden 22");
            moderator.setEmail("moderator@moderator.com");
            moderator.setEnabled(true);
            moderator.setPassword(passwordEncoder.encode("123"));
            moderator.setRoles(Arrays.asList(moderatorRole, userRole));
            userRepository.save(moderator);
        }
    }

    public void registerUser(UserRegisterDto userRegisterDto) {

        User user = new User();
        user.setFirstName(userRegisterDto.getFirstName());
        user.setLastName(userRegisterDto.getLastName());
        user.setAddress(userRegisterDto.getAddress());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        Role userRole = roleRepository.findRoleByName(RoleEnum.USER).orElse(null);
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setEnabled(true);
        userRepository.save(user);
    }
}
