package com.example.mygarden.service;

public interface UserActivationService {

    void cleanUpObsoleteActivationLinks();

    String createActivationCode(String userEmail);


}
