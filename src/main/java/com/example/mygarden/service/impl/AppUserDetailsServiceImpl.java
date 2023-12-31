package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.Role;
import com.example.mygarden.model.entity.User;
import com.example.mygarden.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class AppUserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public AppUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .map(AppUserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    private static UserDetails map(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(AppUserDetailsServiceImpl::map)
                        .collect(Collectors.toList())).build();

        return userDetails;

    }

    private static GrantedAuthority map(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}
