package com.example.mygarden.service;

public interface EmailService {

    void sendRegistrationEmail(String userEmail, String userName, String activationCode);
}
