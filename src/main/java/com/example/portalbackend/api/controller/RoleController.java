package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.RoleUseCase;
import com.example.portalbackend.common.CustomResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/roles")
public class RoleController {
    private final RoleUseCase roleUseCase;

    public RoleController(RoleUseCase roleUseCase) {
        this.roleUseCase = roleUseCase;
    }

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @PageableDefault(size = 10, sort = {"updatedAt"}) Pageable pageable,
            @RequestParam(defaultValue = "") String search) {
        return roleUseCase.findAll(search, pageable);
    }
}
