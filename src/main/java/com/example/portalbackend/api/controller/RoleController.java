package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.role.RoleUpdateData;
import com.example.portalbackend.api.usecase.RoleUseCase;
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
@RequestMapping("/protected/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class RoleController {
    private final RoleUseCase roleUseCase;


    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String search) {
        return roleUseCase.findAll(search, pageable);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<CustomResponse<?>> findAllActive() {
        return roleUseCase.findAllActive();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<?>> update (@Valid @RequestBody RoleUpdateData role, @PathVariable Long id){
        return roleUseCase.update(role, id);
    }
}
