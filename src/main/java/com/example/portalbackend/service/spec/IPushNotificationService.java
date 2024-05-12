package com.example.portalbackend.service.spec;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.Map;

public interface IPushNotificationService {

    void sendPushNotification(Long userId, String title, String message, Map<String, String> data) throws FirebaseMessagingException;

    void sendPushNotificationAllClients(String title, String message, Map<String, String> data) throws FirebaseMessagingException;
    void updateDeviceToken(String deviceToken, Long userId);
    void deleteDeviceToken(Long userId);
}
