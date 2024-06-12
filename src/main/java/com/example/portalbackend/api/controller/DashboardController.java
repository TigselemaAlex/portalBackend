package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.usecase.DashboardUseCase;
import com.example.portalbackend.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
public class DashboardController {

    private final DashboardUseCase dashboardUseCase;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<CustomResponse<?>> getAdminDashboard() {
        return dashboardUseCase.adminDashboard();
    }

    @PreAuthorize("hasAnyRole('ROLE_PRESIDENT' , 'ROLE_VICE_PRESIDENT')")
    @GetMapping("/president")
    public ResponseEntity<CustomResponse<?>> getPresidentDashboard() {
        return dashboardUseCase.presidentDashboard();
    }

    @PreAuthorize("hasRole('ROLE_TREASURER')")
    @GetMapping("/treasure")
    public ResponseEntity<CustomResponse<?>> getTreasureDashboard(
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to) {
        return dashboardUseCase.treasureDashboard(from, to);
    }
}
