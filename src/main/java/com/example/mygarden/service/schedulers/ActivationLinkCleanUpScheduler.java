package com.example.mygarden.service.schedulers;

import com.example.mygarden.service.impl.UserActivationServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ActivationLinkCleanUpScheduler {
    private final UserActivationServiceImpl userActivationService;
    public ActivationLinkCleanUpScheduler(UserActivationServiceImpl userActivationService) {
        this.userActivationService = userActivationService;
    }

    //    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(fixedRate = 10_000,
    initialDelay = 10_000)
    public void cleanUp() {
//        System.out.println("Clean up of activation links. " + LocalDateTime.now());
        //TODO
        userActivationService.cleanUpObsoleteActivationLinks();
    }
}
