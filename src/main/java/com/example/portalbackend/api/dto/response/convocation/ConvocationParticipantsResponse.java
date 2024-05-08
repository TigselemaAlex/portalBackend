package com.example.portalbackend.api.dto.response.convocation;

import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.domain.entity.ConvocationParticipant;

import java.util.Calendar;

public record ConvocationParticipantsResponse(
        Long id,
        String participant,
        Boolean attendance,
        Calendar attendanceDate,
        ResidenceResponse residence
        ) {

    public ConvocationParticipantsResponse (ConvocationParticipant participant){
        this(
                participant.getId(),
                participant.getParticipant(),
                participant.getAttendance(),
                participant.getAttendanceDate(),
                participant.getResidence() == null ? null :
                new ResidenceResponse(participant.getResidence()));
    }
}
