package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.api.usecase.ResidenceUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("protected/residences")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class ResidenceController {

    private final ResidenceUseCase residenceUseCase;


    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAllResidences(
            @RequestParam(required = false, defaultValue = "") String search,
            @PageableDefault(size = 10, sort = "number", direction = Sort.Direction.ASC) Pageable pageable) {
        return residenceUseCase.findAll(search, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PRESIDENT', 'ROLE_VICE_PRESIDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<?>> updateResidence(
            @PathVariable Long id,
            @Valid @RequestBody ResidenceUpdateData residence) {
        return residenceUseCase.update(id, residence);
    }
}
