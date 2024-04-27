package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationUpdateData;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import com.example.portalbackend.domain.repository.ConvocationRepository;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.domain.specifications.ConvocationSpecifications;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.ConvocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
@Transactional
@RequiredArgsConstructor
public class ConvocationService implements IConvocationService {

    private final ConvocationRepository convocationRepository;
    private final IUserService userService;
    private final ResidenceRepository residenceRepository;

    @Override
    public Convocation createConvocation(ConvocationCreateData data) {
        Convocation convocation = Convocation.builder()
                .subject(data.subject())
                .date(CalendarUtil.getCalendar(data.date()))
                .type(data.type())
                .place(data.place())
                .attendanceDeadline(CalendarUtil.getCalendar(data.attendanceDeadline()))
                .createdBy(userService.findById(data.createdBy()))
                .updatedBy(userService.findById(data.createdBy()))
                .finalized(false)
                .build();
        convocation.setParticipants(
                        residenceRepository.findAll().stream()
                                .map(residence -> {
                                    return ConvocationParticipant.builder()
                                            .residence(residence)
                                            .attendance(false)
                                            .convocation(convocation)
                                            .build();
                                }).toList()
                );

        return convocationRepository.save(convocation);
    }

    @Override
    public Page<Convocation> findAll(String subject, Long start,Long end, ConvocationType type, Pageable pageable) {
        Calendar startDate = CalendarUtil.getCalendar(start);
        Calendar endDate = CalendarUtil.getCalendar(end);
        return convocationRepository.findAll(ConvocationSpecifications.withDynamicFilters(subject, startDate, endDate, type), pageable);
    }

    @Override
    public Convocation findById(Long id) {
        return null;
    }

    @Override
    public Convocation updateConvocation(Long id, ConvocationUpdateData data) {
        return null;
    }

    @Override
    public void deleteConvocation(Long id) {

    }

    @Override
    public void finalizeConvocation(Long id) {

    }
}
