package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.dashboard.AdminDashboardResponse;
import com.example.portalbackend.domain.repository.PassageRepository;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.domain.repository.UserRepository;
import com.example.portalbackend.service.spec.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PassageRepository passageRepository;
    private final ResidenceRepository residenceRepository;


    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getAdminDashboard() {

        Long totalUsers = userRepository.count();
        Long totalActiveUsers = userRepository.countUsersByActiveIsTrue();
        Long totalInactiveUsers = userRepository.countUsersByActiveIsFalse();
        Long totalRoles = roleRepository.count();
        Long totalPassages = passageRepository.count();
        Long totalResidences = residenceRepository.count();
        Long totalFreeResidences = residenceRepository.countResidenceByUserIsNull();
        Long totalOccupiedResidences = residenceRepository.countResidenceByUserIsNotNull();

        List<AdminDashboardResponse.UserPerPassage> userPerPassage = new ArrayList<>();
        passageRepository.findAll().forEach(passage -> {
            userPerPassage.add(new AdminDashboardResponse.UserPerPassage(
                    passage.getName(),
                    residenceRepository.countResidenceByUserIsNotNullAndPassage(passage)
            ));
        });

        List<AdminDashboardResponse.UserPerRole> userPerRole = List.of(
                new AdminDashboardResponse.UserPerRole(
                        "ADMIN",
                        userRepository.countUsersByAuthRolesRoleName("ADMIN")),
                new AdminDashboardResponse.UserPerRole(
                        "PRESIDENT",
                        userRepository.countUsersByAuthRolesRoleName("PRESIDENT")),
                new AdminDashboardResponse.UserPerRole(
                        "VICE_PRESIDENT",
                        userRepository.countUsersByAuthRolesRoleName("VICE_PRESIDENT")),
                new AdminDashboardResponse.UserPerRole(
                        "SECRETARY",
                        userRepository.countUsersByAuthRolesRoleName("SECRETARY")),
                new AdminDashboardResponse.UserPerRole(
                        "TREASURER",
                        userRepository.countUsersByAuthRolesRoleName("TREASURER")),
                new AdminDashboardResponse.UserPerRole(
                        "OWNER",
                        userRepository.countUsersByAuthRolesRoleName("OWNER")),
                new AdminDashboardResponse.UserPerRole(
                        "TENANT",
                        userRepository.countUsersByAuthRolesRoleName("TENANT"))

        );

        return new AdminDashboardResponse(
                totalUsers,
                totalActiveUsers,
                totalInactiveUsers,
                totalRoles,
                totalPassages,
                totalResidences,
                totalFreeResidences,
                totalOccupiedResidences,
                userPerPassage,
                userPerRole
        );
    }
}
