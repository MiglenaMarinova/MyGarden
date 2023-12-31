package com.example.mygarden.config;

import com.example.mygarden.repository.UserRepository;
import com.example.mygarden.service.impl.AppUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                authorizeRequest -> authorizeRequest
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/register", "/users/login", "/users/login-error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers("/products/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/admin/manage/**").hasRole("ADMIN")
                        .requestMatchers("/pictures/add", "/moderator/manage").hasRole("MODERATOR")
                        .requestMatchers("/products/all", "/pictures/all").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/buy/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers(HttpMethod.GET, ("/api/products/**")).permitAll()
                        .requestMatchers(HttpMethod.PUT, ("/api/products/**")).permitAll()
                        .requestMatchers(HttpMethod.GET, ("products/change-price/**")).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, ("products/**")).hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new AppUserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
