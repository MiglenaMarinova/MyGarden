package com.example.mygarden.service.impl;

import com.example.mygarden.model.dto.UserRegisterDto;
import com.example.mygarden.model.dto.UserViewDto;
import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.RoleRepository;
import com.example.mygarden.repository.UserRepository;
import com.example.mygarden.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private ModelMapper modelMapper;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, roleService,
                passwordEncoder, applicationEventPublisher, modelMapper);
    }

    @Test
    void registerUserTest() {

        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setFirstName("TestFirstName");
        userRegisterDto.setLastName("TestLastName");
        userRegisterDto.setAddress("TestAddress");
        userRegisterDto.setEmail("test@test.com");
        userRegisterDto.setPassword("testPassword");
        userRegisterDto.setConfirmPassword("testPassword");

        when(modelMapper.map(userRegisterDto, User.class)).thenReturn(new User());

        User user = modelMapper.map(userRegisterDto, User.class);

        userService.registerUser(userRegisterDto);

        verify(userRepository).save(any());
    }

    @Test
    void initTest(){
        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);

        when(roleRepository.save(any())).thenReturn(adminRole);

        User user = new User();
        user.setFirstName("Test");
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        when(userRepository.save(any())).thenReturn(user);

        userService.init();

        verify(userRepository, atLeastOnce()).save(any());
        verify(roleRepository, atLeastOnce()).save(any());
        Assertions.assertNotNull(adminRole);
        Assertions.assertNotNull(user);
    }

    @Test
    void findByEmailTest() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = userService.findByEmail(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());
        Assertions.assertNotNull(result);

    }

    @Test
    void saveUserTest() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.save(user);

        User saved = userRepository.findById(user.getId()).get();

        verify(userRepository).save(any());

        Assertions.assertEquals(user.getId(), saved.getId());

    }

    @Test
    void findAllModerators() {

        String email = "moderatorEmail@test.com";

        Role moderatorRole = new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(moderatorRole));
        user.setPassword("testPassword");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserViewDto.class)).thenReturn(new UserViewDto());

        List<User> moderators = userRepository.findAll()
                .stream()
                .filter(user1 -> user.getRoles().contains(moderatorRole))
                .collect(Collectors.toList());
        List<UserViewDto> moderatorsView = userService.findAllModerators();

        Assertions.assertEquals(moderators.size(), moderatorsView.size());
    }

    @Test
    void findAllAdminsTest() {

        String email = "adminEmail@test.com";

        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(adminRole));
        user.setPassword("testPassword");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserViewDto.class)).thenReturn(new UserViewDto());

        List<User> admins = userRepository.findAll()
                .stream()
                .filter(user1 -> user.getRoles().contains(adminRole))
                .collect(Collectors.toList());
        List<UserViewDto> adminsView = userService.findAllAdmins();

        Assertions.assertEquals(admins.size(), adminsView.size());
    }

    @Test
    void makeAdmin() {
        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        userRole.setId(1L);
        Role adminRole = new Role();
        adminRole.setName(RoleEnum.ADMIN);
        adminRole.setId(2L);
        User user = new User();
        user.setId(1L);
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        user.setPassword("testPassword");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleService.findRoleByName(RoleEnum.ADMIN)).thenReturn(adminRole);

        userService.makeAdmin(user.getId());

        Assertions.assertTrue(user.getRoles().contains(adminRole));

    }

    @Test
    void makeModerator() {

        Role moderatorRole = new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        moderatorRole.setId(1L);
        User user = new User();
        user.setId(1L);
        List<Role> roles = new ArrayList<>();
        user.setRoles(roles);
        user.setPassword("testPassword");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleService.findRoleByName(RoleEnum.MODERATOR)).thenReturn(moderatorRole);

        userService.makeModerator(user.getId());

        Assertions.assertTrue(user.getRoles().contains(moderatorRole));
    }

    @Test
    void removeModerator() {
        Role moderatorRole = new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        moderatorRole.setId(1L);
        User user = new User();
        user.setId(1L);
        List<Role> roles = new ArrayList<>();
        roles.add(moderatorRole);
        user.setRoles(roles);
        user.setPassword("testPassword");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleService.findRoleByName(RoleEnum.MODERATOR)).thenReturn(moderatorRole);

        userService.removeModerator(user.getId());

        Assertions.assertFalse(user.getRoles().contains(moderatorRole));

    }

    @Test
    void findAllUsers() {
        String email = "userEmail@test.com";

        Role userRole = new Role();
        userRole.setName(RoleEnum.USER);
        User user = new User();
        user.setEmail(email);
        user.setId(1L);
        user.setRoles(List.of(userRole));
        user.setPassword("testPassword");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(user, UserViewDto.class)).thenReturn(new UserViewDto());

        List<User> users = userRepository.findAll()
                .stream()
                .filter(user1 -> user.getRoles().contains(userRole))
                .collect(Collectors.toList());
        List<UserViewDto> usersView = userService.findAllUsers();

        Assertions.assertEquals(users.size(), usersView.size());
    }
}