package com.example.portalbackend.api.dto.response.provider;

import com.example.portalbackend.domain.entity.Provider;

public record ProviderResponse(
        Long id,
        String name,
        String description,
        String ruc,
        String address,
        String phone,
        String email,
        String website,
        Boolean active
) {
    public ProviderResponse(Provider provider) {
        this(
                provider.getId(),
                provider.getName(),
                provider.getDescription(),
                provider.getRuc(),
                provider.getAddress(),
                provider.getPhone(),
                provider.getEmail(),
                provider.getWebsite(),
                provider.getActive()
        );
    }
}
