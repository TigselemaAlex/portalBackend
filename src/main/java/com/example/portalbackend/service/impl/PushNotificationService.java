package com.example.portalbackend.service.impl;

import com.example.portalbackend.domain.entity.AuthNotification;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.AuthNotificationRepository;
import com.example.portalbackend.service.spec.IPushNotificationService;
import com.example.portalbackend.service.spec.IUserService;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PushNotificationService implements IPushNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final IUserService userService;
    private final AuthNotificationRepository authNotificationRepository;

    @Override
    public void sendPushNotification(Long userId, String title, String message, Map<String, String> data) throws FirebaseMessagingException {
        AuthNotification authNotification = findAuthNotificationByUserId(userId);
        if(authNotification == null){
            return;
        }
        Message messageObj = Message.builder()
                .putAllData(data)
                .setToken(authNotification.getDeviceToken())
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build()
                )
                .build();
        firebaseMessaging.send(messageObj);
    }

    @Override
    public void sendPushNotificationAllClients(String title, String message, Map<String, String> data) throws FirebaseMessagingException {
        List<AuthNotification> authNotifications = authNotificationRepository.findAllByDeviceTokenNotNull();
        if (authNotifications.isEmpty()) {
            return;
        }
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .putAllData(data)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build()
                )
                .addAllTokens(authNotifications.stream().map(AuthNotification::getDeviceToken).toList())
                .build();

        firebaseMessaging.sendMulticast(multicastMessage);
    }


    public void updateDeviceToken(String deviceToken, Long userId){
        AuthNotification authNotification = findAuthNotificationByUserId(userId);
        if(authNotification == null){
            User user = userService.findById(userId);
            authNotification = new AuthNotification();
            authNotification.setUser(user);
        }
        if (deviceToken.equals(authNotification.getDeviceToken())) {
            return;
        }
        authNotification.setDeviceToken(deviceToken);
        authNotificationRepository.save(authNotification);
    }

    @Override
    public void deleteDeviceToken(Long userId) {
        AuthNotification authNotification = findAuthNotificationByUserId(userId);
        if(authNotification == null){
            return;
        }
        authNotificationRepository.delete(authNotification);
    }

    private AuthNotification findAuthNotificationByUserId(Long userId){
        return authNotificationRepository.findByUserId(userId).orElse(null);
    }
}
