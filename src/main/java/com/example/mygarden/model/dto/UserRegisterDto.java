package com.example.mygarden.model.dto;

import com.example.mygarden.validation.FieldMatch;
import com.example.mygarden.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
@FieldMatch(first = "password",
        second = "confirmPassword",
        message = "passwords should match")
public class UserRegisterDto {

    @NotNull
    @Size(min = 2, max = 20, message = "First name length should be between 2 and 20")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20, message = "Last name length should be between 2 and 20")
    private String lastName;
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please enter a valid email address")
    @UniqueUserEmail
    private String email;
    @NotNull
    @Size(min = 3, max = 20, message = "Password length should be between 3 and 20")
    private String password;
    @NotNull
    @Size(min = 3, max = 20, message = "Password length should be between 3 and 20")
    private String confirmPassword;
    @NotNull
    @Size(min = 5, max = 20, message = "Address length should be between 2 and 20")
    private String address;

    public UserRegisterDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}




