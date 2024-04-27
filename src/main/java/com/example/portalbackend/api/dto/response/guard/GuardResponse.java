package com.example.portalbackend.api.dto.response.guard;

import com.example.portalbackend.domain.entity.Guard;

public record GuardResponse(
        Long id,
        String dni,
        String fullName,
        String phone,
        String photoUrl,
        Boolean active,
        Long pendingActivities,
        Long inProgressActivities,
        Long finishedActivities,
        Long incompleteActivities

) {

    public GuardResponse(Guard guard){
        this(
                guard.getId(),
                guard.getDni(),
                guard.getFullName(),
                guard.getPhone(),
                guard.getPhotoUrl(),
                guard.getActive(),
                0L,
                0L,
                0L,
                0L
        );
    }

    public GuardResponse(Guard guard, Long pendingActivities, Long inProgressActivities, Long finishedActivities, Long incompleteActivities){
        this(
                guard.getId(),
                guard.getDni(),
                guard.getFullName(),
                guard.getPhone(),
                guard.getPhotoUrl(),
                guard.getActive(),
                pendingActivities,
                inProgressActivities,
                finishedActivities,
                incompleteActivities
        );
    }
}
