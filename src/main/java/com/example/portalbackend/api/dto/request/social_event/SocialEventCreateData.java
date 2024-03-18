package com.example.portalbackend.api.dto.request.social_event;

import java.util.Calendar;

public record SocialEventCreateData(
        String title,
        String description,
        String place,
        Calendar date,
        String imageUrl,
        Long createdBy
) {
}
