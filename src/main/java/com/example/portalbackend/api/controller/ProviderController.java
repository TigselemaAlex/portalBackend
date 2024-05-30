package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.provider.ProviderCreateData;
import com.example.portalbackend.api.dto.request.provider.ProviderUpdateData;
import com.example.portalbackend.api.usecase.ProviderUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderUseCase providerUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll() {
        return providerUseCase.findAll();
    }

    @GetMapping("/active")
    public ResponseEntity<CustomResponse<?>> findAllActive() {
        return providerUseCase.findAllActive();
    }

    @PostMapping
    public ResponseEntity<CustomResponse<?>> save(@Valid @RequestBody ProviderCreateData data) {
        return providerUseCase.save(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> update(@PathVariable Long id, @Valid @RequestBody ProviderUpdateData data) {
        return providerUseCase.update(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id) {
        return providerUseCase.delete(id);
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<CustomResponse<?>> reactivate(@PathVariable Long id) {
        return providerUseCase.reactivate(id);
    }

}
