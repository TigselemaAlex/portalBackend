package com.example.portalbackend.api.dto.response.convocation;

import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;

import java.util.Calendar;

public record ConvocationResponse(
        Long id,
        String subject,
        String description,
        String code,
        Calendar date,
        ConvocationType type,
        String place,
        Calendar attendanceDeadline,
        UserResponse createdBy,
        UserResponse updatedBy,
        Boolean finalized,
        Integer totalPresent,
        Integer totalMissing
) {

    public ConvocationResponse(Convocation convocation, Integer totalPresents, Integer totalMissing) {
        this(
                convocation.getId(),
                convocation.getSubject(),
                convocation.getDescription(),
                convocation.getCode(),
                convocation.getDate(),
                convocation.getType(),
                convocation.getPlace(),
                convocation.getAttendanceDeadline(),
                convocation.getCreatedBy() == null ? null : new UserResponse(convocation.getCreatedBy()),
                convocation.getUpdatedBy() == null ? null : new UserResponse(convocation.getUpdatedBy()),
                convocation.getFinalized(),
                totalPresents,
                totalMissing
        );
    }
}
