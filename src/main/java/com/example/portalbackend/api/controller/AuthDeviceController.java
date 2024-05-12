package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.auth.AuthDeviceData;
import com.example.portalbackend.service.spec.IPushNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/protected/auth-device")
public class AuthDeviceController {

    private final IPushNotificationService pushNotificationService;

    @PostMapping
    public void updateDeviceToken(@Valid @RequestBody AuthDeviceData data) {
        pushNotificationService.updateDeviceToken(data.deviceToken(), data.userId());
    }
}
