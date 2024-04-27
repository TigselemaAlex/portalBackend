package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.geolocation.GeolocationData;
import com.example.portalbackend.api.usecase.GeolocationUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("protected/geolocation")
@RequiredArgsConstructor
public class GeolocationController {

    private final GeolocationUseCase geolocationUseCase;

    @GetMapping
    public ResponseEntity<GeolocationData> getConfig() throws IOException {
        return geolocationUseCase.getConfig();
    }

    @PutMapping
    public ResponseEntity<CustomResponse<?>> updateConfig(@Valid @RequestBody GeolocationData data) throws IOException {

        return geolocationUseCase.updateConfig(data);
    }

}
