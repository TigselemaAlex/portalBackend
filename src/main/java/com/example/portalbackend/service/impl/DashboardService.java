package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.dashboard.AdminDashboardResponse;
import com.example.portalbackend.api.dto.response.dashboard.PresidentDashboardResponse;
import com.example.portalbackend.domain.repository.*;
import com.example.portalbackend.service.spec.IDashboardService;
import com.example.portalbackend.util.enumerate.ConvocationType;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PassageRepository passageRepository;
    private final ResidenceRepository residenceRepository;

    private final GuardActivityRepository guardActivityRepository;
    private final GuardIncidentRepository guardIncidentRepository;
    private final SocialEventRepository socialEventRepository;
    private final ConvocationRepository convocationRepository;
    private final IncidentTypeRepository incidentTypeRepository;
    private final ParkingRepository parkingRepository;
    private final ParkingTypeRepository parkingTypeRepository;


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

    @Override
    public PresidentDashboardResponse getPresidentDashboard() {

        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfMonth.set(Calendar.MINUTE, 0);
        firstDayOfMonth.set(Calendar.SECOND, 0);

        Calendar lastDayOfMonth = Calendar.getInstance();
        lastDayOfMonth.set(Calendar.DAY_OF_MONTH, lastDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastDayOfMonth.set(Calendar.HOUR_OF_DAY, 23);
        lastDayOfMonth.set(Calendar.MINUTE, 59);
        lastDayOfMonth.set(Calendar.SECOND, 59);


        List<PresidentDashboardResponse.ParkingUsageDash> parkingUsage = parkingTypeRepository.findAll().stream().map(
                parkingType -> new PresidentDashboardResponse.ParkingUsageDash(
                        parkingType.getType(),
                        parkingRepository.countByGroupTypeId(parkingType.getId()),
                        parkingRepository.countByGroupTypeIdAndResidenceIsNotNull(parkingType.getId())
                )
        ).toList();


        Long totalMonthlySocialEvents = socialEventRepository.countByDateBetween(firstDayOfMonth, lastDayOfMonth);

        Long totalMonthlyConvocationsAssembly = convocationRepository.countByDateBetweenAndFinalizedIsTrueAndType(firstDayOfMonth, lastDayOfMonth, ConvocationType.ASSEMBLY_ORDINARY) +
                convocationRepository.countByDateBetweenAndFinalizedIsTrueAndType(firstDayOfMonth, lastDayOfMonth, ConvocationType.ASSEMBLY_EXTRAORDINARY);

        Long totalMonthlyConvocationsSession = convocationRepository.countByDateBetweenAndFinalizedIsTrueAndType(firstDayOfMonth, lastDayOfMonth, ConvocationType.SESSION);

        Long totalMonthlyConvocationsMeeting = convocationRepository.countByDateBetweenAndFinalizedIsTrueAndType(firstDayOfMonth, lastDayOfMonth, ConvocationType.MEETING_ORDINARY)
                + convocationRepository.countByDateBetweenAndFinalizedIsTrueAndType(firstDayOfMonth, lastDayOfMonth, ConvocationType.MEETING_EXTRAORDINARY);

        List<PresidentDashboardResponse.GuardActivityStatusDas> monthlyGuardActivity =
            List.of(
                new PresidentDashboardResponse.GuardActivityStatusDas("Pendiente", guardActivityRepository.countByStartDateBetweenAndStatus(firstDayOfMonth, lastDayOfMonth, GuardActivityStatus.PENDING)),
                new PresidentDashboardResponse.GuardActivityStatusDas("Finalizado", guardActivityRepository.countByStartDateBetweenAndStatus(firstDayOfMonth, lastDayOfMonth, GuardActivityStatus.FINISHED)),
                new PresidentDashboardResponse.GuardActivityStatusDas("En progreso", guardActivityRepository.countByStartDateBetweenAndStatus(firstDayOfMonth, lastDayOfMonth, GuardActivityStatus.IN_PROGRESS)),
                new PresidentDashboardResponse.GuardActivityStatusDas("Incompletas", guardActivityRepository.countByStartDateBetweenAndStatus(firstDayOfMonth, lastDayOfMonth, GuardActivityStatus.INCOMPLETE))
                );

        List<PresidentDashboardResponse.GuardIncidentTypeDash> monthlyGuardIncidentType = incidentTypeRepository.findAll().stream().map(
                incidentType -> new PresidentDashboardResponse.GuardIncidentTypeDash(
                        incidentType.getName(),
                        guardIncidentRepository.countByDateBetweenAndType(firstDayOfMonth, lastDayOfMonth, incidentType),
                        incidentType.getSeverity()
                )
        ).toList();

        return new PresidentDashboardResponse(
                parkingUsage,
                totalMonthlySocialEvents,
                totalMonthlyConvocationsAssembly,
                totalMonthlyConvocationsMeeting,
                totalMonthlyConvocationsSession,
                monthlyGuardActivity,
                monthlyGuardIncidentType
        );
    }
}
