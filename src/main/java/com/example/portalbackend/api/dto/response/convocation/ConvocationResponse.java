package com.example.portalbackend.api.dto.response.convocation;

import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;

import java.util.Calendar;

public record ConvocationResponse(
        Long id,
        String subject,
        Calendar date,
        ConvocationType type,
        String place,
        Calendar attendanceDeadline,
        UserResponse createdBy,
        UserResponse updatedBy,
        Boolean finalized
) {

    public ConvocationResponse(Convocation convocation) {
        this(
                convocation.getId(),
                convocation.getSubject(),
                convocation.getDate(),
                convocation.getType(),
                convocation.getPlace(),
                convocation.getAttendanceDeadline(),
                convocation.getCreatedBy() == null ? null : new UserResponse(convocation.getCreatedBy()),
                convocation.getUpdatedBy() == null ? null : new UserResponse(convocation.getUpdatedBy()),
                convocation.getFinalized()
        );
    }
}
