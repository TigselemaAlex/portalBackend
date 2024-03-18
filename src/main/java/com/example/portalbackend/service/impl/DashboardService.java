package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.dashboard.AdminDashboardResponse;
import com.example.portalbackend.domain.repository.PassageRepository;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.domain.repository.UserRepository;
import com.example.portalbackend.service.spec.IDashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DashboardService implements IDashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PassageRepository passageRepository;
    private final ResidenceRepository residenceRepository;

    public DashboardService(UserRepository userRepository, RoleRepository roleRepository, PassageRepository passageRepository, ResidenceRepository residenceRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passageRepository = passageRepository;
        this.residenceRepository = residenceRepository;
    }

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
        List<AdminDashboardResponse.UserPerPassage> userPerPassage = List.of(
                new AdminDashboardResponse.UserPerPassage(
                        "Calle Caracas",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Calle Caracas")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje Maracay",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje Maracay")),
                new AdminDashboardResponse.UserPerPassage(
                        "Calle Corrientes",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Calle Corrientes")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje Osorno",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje Osorno")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje Ica",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje Ica")),
                new AdminDashboardResponse.UserPerPassage(
                        "Puerto Principe",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Puerto Principe")),
                new AdminDashboardResponse.UserPerPassage(
                        "Calle Iquique",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Calle Iquique")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje Corrientes",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje Corrientes")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje Rosario",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje Rosario")),
                new AdminDashboardResponse.UserPerPassage(
                        "Pasaje San Estanislao",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Pasaje San Estanislao")),
                new AdminDashboardResponse.UserPerPassage(
                        "Cale Tucumán",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Cale Tucumán")),
                new AdminDashboardResponse.UserPerPassage(
                        "Calle Concepción",
                        residenceRepository.
                                countResidenceByUserIsNotNullAndPassageName("Calle Concepción"))
        );

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
