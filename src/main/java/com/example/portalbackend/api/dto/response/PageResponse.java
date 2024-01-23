package com.example.portalbackend.api.dto.response;

import org.springframework.data.domain.Page;

public record PageResponse(
        Object content,
        int currentPage,
        long totalItems,
        int totalPages
) {
    public PageResponse(Page<?> page){
        this(page.getContent(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }
}
