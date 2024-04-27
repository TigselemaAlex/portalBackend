package com.example.portalbackend.service.generator;

import lombok.NonNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {
    private final DataGeneratorService dataGeneratorService;

    public DataLoader(DataGeneratorService dataGeneratorService) {
        this.dataGeneratorService = dataGeneratorService;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        System.out.println("Loading data");
        /*dataGeneratorService.generateRoles();
        dataGeneratorService.generateUsers();
        dataGeneratorService.generatePassages();
        dataGeneratorService.generateResidences();
        dataGeneratorService.assignUsersToResidences();
        dataGeneratorService.generateParkingTypes();
        dataGeneratorService.generateParkingGroups();
        dataGeneratorService.generateParkings();
        dataGeneratorService.generateSocialEvents();
        dataGeneratorService.generateGuards();
        dataGeneratorService.generateGuardActivities();
        dataGeneratorService.generateIncidentTypes();*/
        System.out.println("Data loaded");
    }
}
