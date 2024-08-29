package com.example.portalbackend.api.dto.response.residence_history;

import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.domain.entity.ResidenceHistory;

import java.util.Calendar;

public record ResidenceHistoryResponse(
        Calendar startDate,
        Calendar finishDate,
        ResidenceResponse residence,
        UserResponse user
) {
    public ResidenceHistoryResponse (ResidenceHistory history){
        this(
                history.getStartDate(),
                history.getFinishDate(),
                history.getResidence() != null ? new ResidenceResponse(history.getResidence()) : null,
                history.getUser() != null ? new UserResponse(history.getUser()): null
        );
    }
}
