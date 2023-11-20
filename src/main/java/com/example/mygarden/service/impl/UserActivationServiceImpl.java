package com.example.mygarden.service.impl;

import com.example.mygarden.model.entity.UserActivationCode;
import com.example.mygarden.model.events.UsersRegisteredEvent;
import com.example.mygarden.repository.UserActivationCodeRepository;
import com.example.mygarden.repository.UserRepository;
import com.example.mygarden.service.EmailService;
import com.example.mygarden.service.UserActivationService;
import com.example.mygarden.service.exeption.ObjectNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private static final String ACTIVATION_CODE_SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ACTIVATION_CODE_LENGTH = 17;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserActivationCodeRepository userActivationCodeRepository;



    public UserActivationServiceImpl(EmailService emailService, UserRepository userRepository,
                                     UserActivationCodeRepository userActivationCodeRepository) {
        this.emailService = emailService;

        this.userRepository = userRepository;
        this.userActivationCodeRepository = userActivationCodeRepository;
    }

    @EventListener(UsersRegisteredEvent.class)
    public void userRegistered(UsersRegisteredEvent event) {
        String activationCode = createActivationCode(event.getUserEmail());
        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserName(), activationCode);
    }

    public void cleanUpObsoleteActivationLinks(){
        //TODO implement


    }

    public  String createActivationCode(String userEmail) {

        String activationCode = generateActivationCode();

        UserActivationCode userActivationCode = new UserActivationCode();

        userActivationCode.setActivationCode(activationCode);
        userActivationCode.setCreated(Instant.now());
        userActivationCode.setUser(userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ObjectNotFoundException("User with email " + userEmail + " not found")));

        userActivationCodeRepository.save(userActivationCode);

        return activationCode;
    }

    private static String generateActivationCode(){
        Random random = new SecureRandom();
        StringBuilder activationCode = new StringBuilder();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(randomIndex));
        }

        return activationCode.toString();
    }
}
