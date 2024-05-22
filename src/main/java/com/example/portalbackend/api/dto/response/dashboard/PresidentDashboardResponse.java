package com.example.portalbackend.api.dto.response.dashboard;

import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record PresidentDashboardResponse(

        List<ParkingUsageDash> parkingUsage,
        Long totalMonthlySocialEvents,
        Long totalMonthlyConvocationAssembly,
        Long totalMonthlyConvocationMeeting,
        Long totalMonthlyConvocationSession,
        List<GuardActivityStatusDas> monthlyGuardActivity,
        List<GuardIncidentTypeDash> monthlyGuardIncidentType

) {
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class ParkingUsageDash{
        private String type;
        private Long total;
        private Long used;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class GuardActivityStatusDas{
        private String status;
        private Long total;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class GuardIncidentTypeDash{
        private String type;
        private Long total;
        private String color;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class GuardIncidentStatus{
        private String status;
        private Long total;
    }
}
