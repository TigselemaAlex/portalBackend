package com.example.portalbackend.api.dto.response.minutes;

import com.example.portalbackend.domain.entity.Minutes;

public record MinutesResponse(
        Long id,
        String fileUrl,
        String fileName
) {
    public MinutesResponse(Minutes minutes){
        this(minutes.getId(), minutes.getFileUrl(), minutes.getFileName());
    }
}
