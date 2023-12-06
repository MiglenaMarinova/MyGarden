package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.model.enums.RoleEnum;
import com.example.mygarden.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class AppUserDetailsServiceTest {

    public AppUserDetailsServiceImpl serviceTest;
    @Mock
    private UserRepository mockUserRepository;


    @BeforeEach
    void setUp(){
        serviceTest = new AppUserDetailsServiceImpl(mockUserRepository);
    }

//    @Test
//    void testMock(){
//
//        User user = new User();
//        user.setFirstName("Lena");
//
//        when(mockUserRepository.findByEmail("test@test.com"))
//                .thenReturn(Optional.of(user));
//        Optional<User> optUser = mockUserRepository.findByEmail("lena@test.com");
//
//        User user1 = optUser.get();
//
//        Assertions.assertEquals("Lena", user1.getFirstName());
//    }

    @Test
    void testUserNotFound(){
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceTest.loadUserByUsername("test@test.com"));

    }

    @Test
    void testUseFoundException(){
        //Arrange
        User testUser= createTestUser();
        when(mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));
        //Act
        UserDetails userDetails = serviceTest.loadUserByUsername(testUser.getEmail());

        //Assert
        assertNotNull(userDetails);
        assertEquals(
             testUser.getEmail(),
                userDetails.getUsername(),
                "Username is not mapped to email"
        );

        assertEquals(testUser.getPassword(), userDetails.getPassword());
        assertEquals(3, userDetails.getAuthorities().size());
        assertTrue(containsAuthority(userDetails, "ROLE_" + RoleEnum.USER),
                "The User is not user");
        assertTrue(containsAuthority(userDetails, "ROLE_" + RoleEnum.ADMIN),
                "The User is not admin");
        assertTrue(containsAuthority(userDetails, "ROLE_" + RoleEnum.MODERATOR),
                "The User is not moderator");
    }


    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority){
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(a->expectedAuthority.equals(a.getAuthority()));
    }





    private static User createTestUser(){
        User testUser = new User();
        testUser.setFirstName("firstName");
        testUser.setLastName("lastName");
        testUser.setEmail("test@test.com");
        testUser.setAddress("address");
        testUser.setPassword("123");
        Role userRole= new Role();
        userRole.setName(RoleEnum.USER);
        Role adminRole= new Role();
        adminRole.setName(RoleEnum.ADMIN);
        Role moderatorRole= new Role();
        moderatorRole.setName(RoleEnum.MODERATOR);
        testUser.setRoles(List.of(
           userRole, adminRole, moderatorRole
        ));


        return testUser;
    }
}
