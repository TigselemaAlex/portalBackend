package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.dashboard.AdminDashboardResponse;
import com.example.portalbackend.api.dto.response.dashboard.PresidentDashboardResponse;

public interface IDashboardService {
    AdminDashboardResponse getAdminDashboard();

PresidentDashboardResponse getPresidentDashboard();
}
