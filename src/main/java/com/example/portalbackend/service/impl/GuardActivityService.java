package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityCreateData;
import com.example.portalbackend.api.dto.request.guard_activity.GuardActivityUpdateData;
import com.example.portalbackend.domain.entity.Guard;
import com.example.portalbackend.domain.entity.GuardActivity;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.GuardActivityRepository;
import com.example.portalbackend.service.spec.IGuardActivityService;
import com.example.portalbackend.service.spec.IGuardService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.GuardActivityStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GuardActivityService implements IGuardActivityService {

    private final GuardActivityRepository guardActivityRepository;

    private final IGuardService guardService;

    private final IUserService userService;
    @Override
    @Transactional(readOnly = true)
    public Long countByGuardAndStatus(Guard guard, GuardActivityStatus status) {
        return guardActivityRepository.countByGuardAndStatus(guard, status);
    }

    @Override
    public GuardActivity create(GuardActivityCreateData data) {
        Guard guard = guardService.findById(data.guard());
        User user = userService.findById(data.createdBy());
        GuardActivity guardActivity = GuardActivity.builder()
                .subject(data.subject())
                .description(data.description())
                .startDate(CalendarUtil.getCalendar(data.startDate()))
                .endDate(CalendarUtil.getCalendar(data.endDate()))
                .guard(guard)
                .createdBy(user)
                .status(GuardActivityStatus.PENDING)
                .build();
        return guardActivityRepository.save(guardActivity);
    }

    @Override
    public Page<GuardActivity> findAll(String subject, Pageable pageable) {
        return guardActivityRepository.findAllBySubjectContainingIgnoreCase(subject, pageable);
    }

    @Override
    public GuardActivity findById(Long id) {
        return guardActivityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public GuardActivity update(Long id, GuardActivityUpdateData data) {
        GuardActivity guardActivity = findById(id);
        Guard guard = guardService.findById(data.guard());
        guardActivity.setSubject(data.subject());
        guardActivity.setDescription(data.description());
        guardActivity.setStartDate(CalendarUtil.getCalendar(data.startDate()));
        guardActivity.setEndDate(CalendarUtil.getCalendar(data.endDate()));
        guardActivity.setStatus(data.status());
        guardActivity.setObservation(data.observation());
        guardActivity.setGuard(guard);
        return guardActivityRepository.save(guardActivity);
    }

    @Override
    public void delete(Long id) {
        GuardActivity guardActivity = findById(id);
        guardActivityRepository.delete(guardActivity);
    }
}
