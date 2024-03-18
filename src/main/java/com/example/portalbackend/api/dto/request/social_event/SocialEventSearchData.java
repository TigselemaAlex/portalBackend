package com.example.portalbackend.api.dto.request.social_event;

import java.util.Calendar;

public record SocialEventSearchData(
        Calendar to,
        Calendar from
) {
}
