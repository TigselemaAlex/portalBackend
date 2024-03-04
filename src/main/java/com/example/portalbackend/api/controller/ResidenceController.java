package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.api.usecase.ResidenceUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("protected/residences")
public class ResidenceController {

    private final ResidenceUseCase residenceUseCase;

    public ResidenceController(ResidenceUseCase residenceUseCase) {
        this.residenceUseCase = residenceUseCase;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAllResidences(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "number", direction = Sort.Direction.ASC) Pageable pageable) {
        return residenceUseCase.findAll(search, pageable);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateResidence(
            @PathVariable Long id,
            @Valid @RequestBody ResidenceUpdateData residence) {
        return residenceUseCase.update(id, residence);
    }
}
