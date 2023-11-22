package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.UserRegisterDto;
import com.example.mygarden.model.dto.UserViewDto;
import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.model.events.UsersRegisteredEvent;
import com.example.mygarden.repository.RoleRepository;
import com.example.mygarden.repository.UserRepository;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements com.example.mygarden.service.UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher applicationEventPublisher,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
        this.modelMapper = modelMapper;
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
        user.setEnabled(false);
        userRepository.save(user);

        applicationEventPublisher.publishEvent(new UsersRegisteredEvent(
                "UserService", userRegisterDto.getEmail(), userRegisterDto.getFirstName()
        ));
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + username + " not found"));
    }

    @Override
    public void save(User userBuyer) {
        userRepository.save(userBuyer);
    }

    @Override
    public List<UserViewDto> findAllModerators() {
        return userRepository.findAll()
                .stream()
                .filter(this::hasRoleModerator)

                .map(user -> modelMapper.map(user, UserViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserViewDto> findAllAdmins() {
        return userRepository.findAll()
                .stream()
                .filter(this::hasRoleAdmin)
                .map(user -> modelMapper.map(user, UserViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserViewDto> findAllUsers() {

        return userRepository.findAll()
                .stream()
                .filter(this::hasRoleUser)
                .map(user1 -> modelMapper.map(user1, UserViewDto.class))
                .collect(Collectors.toList());

    }
    private boolean hasRoleUser(User user){
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .noneMatch(roleEnum -> roleEnum == RoleEnum.MODERATOR);
    }
    private boolean hasRoleModerator(User user){
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .anyMatch(roleEnum -> roleEnum == RoleEnum.MODERATOR);
    }
    private boolean hasRoleAdmin(User user){
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .anyMatch(roleEnum -> roleEnum == RoleEnum.ADMIN);
    }
}
