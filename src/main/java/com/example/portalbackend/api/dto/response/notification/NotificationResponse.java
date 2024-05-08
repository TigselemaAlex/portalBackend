package com.example.portalbackend.api.dto.response.notification;

public record NotificationResponse(
        String title,
        String message,
        String user
) {
}
