package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.dashboard.AdminDashboardResponse;
import com.example.portalbackend.api.dto.response.dashboard.PresidentDashboardResponse;
import com.example.portalbackend.api.dto.response.dashboard.TreasureDashboardResponse;

import java.util.Calendar;

public interface IDashboardService {
    AdminDashboardResponse getAdminDashboard();

    PresidentDashboardResponse getPresidentDashboard();

    TreasureDashboardResponse getTreasureDashboard(Calendar from, Calendar to);

}
