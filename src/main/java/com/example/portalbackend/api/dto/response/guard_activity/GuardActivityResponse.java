package com.example.portalbackend.api.dto.response.guard_activity;

import com.example.portalbackend.api.dto.response.guard.GuardResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;

import java.util.Calendar;

public record GuardActivityResponse(
        Long id,
        String subject,
        String description,
        Calendar startDate,
        Calendar endDate,
        GuardActivityStatus status,
        String observation,
        GuardResponse guard,
        UserResponse createdBy
) {
        public GuardActivityResponse(GuardActivity guardActivity){
            this(
                    guardActivity.getId(),
                    guardActivity.getSubject(),
                    guardActivity.getDescription(),
                    guardActivity.getStartDate(),
                    guardActivity.getEndDate(),
                    guardActivity.getStatus(),
                    guardActivity.getObservation(),
                    guardActivity.getGuard() != null? new GuardResponse(guardActivity.getGuard()) : null,
                    guardActivity.getCreatedBy() != null? new UserResponse(guardActivity.getCreatedBy()) : null
            );
        }
}
