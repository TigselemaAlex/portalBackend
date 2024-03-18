package com.example.portalbackend.api.dto.response.social_event;

import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.SocialEvent;

import java.util.Calendar;

public record SocialEventResponse(
        Long id,
        String title,
        String description,
        String place,
        Calendar date,
        String imageUrl,
        UserResponse createdBy,
        UserResponse updatedBy
) {
    public SocialEventResponse(SocialEvent socialEvent) {
        this(
                socialEvent.getId(),
                socialEvent.getTitle(),
                socialEvent.getDescription(),
                socialEvent.getPlace(),
                socialEvent.getDate(),
                socialEvent.getImageUrl(),
                new UserResponse(socialEvent.getCreatedBy()),
                new UserResponse(socialEvent.getUpdatedBy())
        );
    }
}
