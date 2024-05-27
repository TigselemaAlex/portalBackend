package com.example.portalbackend.api.dto.response.dashboard;

import lombok.*;

import java.util.List;

public record AdminDashboardResponse(
        Long totalUsers,
        Long totalActiveUsers,
        Long totalInactiveUsers,
        Long totalRoles,
        Long totalPassages,
        Long totalResidences,
        Long totalFreeResidences,
        Long totalOccupiedResidences,
        List<UserPerPassage> userPerPassage,
        List<UserPerRole> userPerRole

) {


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class UserPerPassage {
        private String passageName;
        private Long totalUsers;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class UserPerRole {
        private String roleName;
        private Long totalUsers;
    }


}
