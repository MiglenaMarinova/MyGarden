package com.example.mygarden.testUtil;

import com.example.mygarden.model.entity.Order;
import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.RoleRepository;
import com.example.mygarden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTestData {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    public User createTestUser(String email){
        User testUser = new User();

        testUser.setFirstName("User");
        testUser.setLastName("Userov");
        testUser.setPassword("123");
        testUser.setEmail(email);
        testUser.setAddress("Userova 22");
        List<Order> orders = new ArrayList<>();
        testUser.setOrders(orders);

        Role userRole = roleRepository.findRoleByName(RoleEnum.USER).orElse(null);
        assert userRole != null;
        testUser.setRoles(
                List.of(userRole)
        );
        return userRepository.save(testUser);
    }
    public User createTestAdmin(String email){
        User testAdmin = new User();
        testAdmin.setFirstName("Admin");
        testAdmin.setLastName("Adminov");
        testAdmin.setPassword("123");
        testAdmin.setEmail(email);
        testAdmin.setAddress("Userova 22");
        Role adminRole = roleRepository.findRoleByName(RoleEnum.ADMIN).orElse(null);
        assert adminRole != null;
        testAdmin.setRoles(
                List.of(adminRole)
        );
        return userRepository.save(testAdmin);
    }
    public User createTestModerator(String email){
        User testModerator = new User();
        testModerator.setFirstName("Moderator");
        testModerator.setLastName("Moderator");
        testModerator.setPassword("123");
        testModerator.setEmail(email);
        testModerator.setAddress("Userova 22");
        Role moderatorRole = roleRepository.findRoleByName(RoleEnum.MODERATOR).orElse(null);
        assert moderatorRole != null;
        testModerator.setRoles(
                List.of(moderatorRole)
        );
        return userRepository.save(testModerator);
    }

    public void cleanAllTestData(){

        userRepository.deleteAll();
    }

}
