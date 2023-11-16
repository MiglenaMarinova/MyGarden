package com.example.mygarden.service;

import com.example.mygarden.model.events.UsersRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationService {

    private final EmailService emailService;

    public UserActivationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(UsersRegisteredEvent.class)
    void userRegistered(UsersRegisteredEvent event) {

        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserName());
    }
}
