package com.example.portalbackend.api.dto.request.convocation;

import com.example.portalbackend.util.enumerate.ConvocationType;

public record ConvocationCreateData(
        String subject,
        String description,
        String place,
        Long date,
        ConvocationType type,
        Long attendanceDeadline,
        Long createdBy
) {
}
