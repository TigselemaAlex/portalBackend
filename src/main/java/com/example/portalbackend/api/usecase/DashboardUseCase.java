package com.example.portalbackend.api.usecase;

import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IDashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DashboardUseCase extends AbstractUseCase{

    private final IDashboardService dashboardService;
    protected DashboardUseCase(CustomResponseBuilder customResponseBuilder, IDashboardService dashboardService) {
        super(customResponseBuilder);
        this.dashboardService = dashboardService;
    }

    public ResponseEntity<CustomResponse<?>> adminDashboard() {
        return customResponseBuilder.build(HttpStatus.OK,"Dashboard del Administrador" ,dashboardService.getAdminDashboard());
    }
}
