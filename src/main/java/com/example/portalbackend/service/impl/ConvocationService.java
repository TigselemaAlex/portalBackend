package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.convocation.ConvocationAttendanceData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationUpdateData;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.ConvocationParticipantRepository;
import com.example.portalbackend.domain.repository.ConvocationRepository;
import com.example.portalbackend.domain.repository.ResidenceRepository;
import com.example.portalbackend.domain.specifications.ConvocationSpecifications;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.ConvocationType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ConvocationService implements IConvocationService {

    private final ConvocationRepository convocationRepository;
    private final ConvocationParticipantRepository convocationParticipantRepository;
    private final IUserService userService;
    private final ResidenceRepository residenceRepository;
    private final ResidenceService residenceService;

    @Override
    public Convocation createConvocation(ConvocationCreateData data) {
        Convocation convocation = Convocation.builder()
                .subject(data.subject())
                .description(data.description())
                .date(CalendarUtil.getCalendar(data.date()))
                .type(data.type())
                .place(data.place())
                .code(generateCode(data.type()))
                .attendanceDeadline(CalendarUtil.getCalendar(data.attendanceDeadline()))
                .createdBy(userService.findById(data.createdBy()))
                .updatedBy(userService.findById(data.createdBy()))
                .finalized(false)
                .build();
        if (data.type().equals(ConvocationType.ASSEMBLY_EXTRAORDINARY) || data.type().equals(ConvocationType.ASSEMBLY_ORDINARY)){
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


        }
        return convocationRepository.save(convocation);
    }

    @Override
    public Page<Convocation> findAll(String subject, Long start, Long end, ConvocationType type, Pageable pageable) {
        Calendar startDate = CalendarUtil.getCalendar(start);
        Calendar endDate = CalendarUtil.getCalendar(end);
        return convocationRepository.findAll(ConvocationSpecifications.withDynamicFilters(subject, startDate, endDate, type), pageable);
    }

    @Override
    public Convocation findById(Long id) {
        return convocationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }



    @Override
    public Convocation updateConvocation(Long id, ConvocationUpdateData data) {
        Convocation convocation = findById(id);
        convocation.setSubject(data.subject());
        convocation.setDescription(data.description());
        convocation.setPlace(data.place());
        convocation.setDate(CalendarUtil.getCalendar(data.date()));
        convocation.setType(data.type());
        convocation.setFinalized(data.finalized());
        convocation.setAttendanceDeadline(CalendarUtil.getCalendar(data.attendanceDeadline()));
        convocation.setUpdatedBy(userService.findById(data.updatedBy()));
        return convocationRepository.save(convocation);
    }

    @Override
    public void deleteConvocation(Long id) {
        Convocation convocation = findById(id);
        convocationRepository.delete(convocation);
    }

    @Override
    public void finalizeConvocation(Long id) {
        Convocation convocation = findById(id);
        convocation.setFinalized(true);
        convocation.getAssemblyQuestions().forEach(assemblyQuestion -> {
            assemblyQuestion.setEnabled(false);
        });
        convocationRepository.save(convocation);
    }

    @Override
    public Integer countTotalMissing(Long id) {
        return Math.toIntExact(convocationParticipantRepository.countByConvocationIdAndAttendanceIsFalse(id));
    }

    @Override
    public ConvocationParticipant updateAttendance(Long id, ConvocationAttendanceData data) {
        ConvocationParticipant participant = findParticipantById(id);

        Residence residence = residenceService.findById(data.residence());
        User user = residence.getUser();


        if (data.attendance()){
            if(Objects.nonNull(user)){
                participant.setAttendance(true);
                participant.setAttendanceDate(Calendar.getInstance());
                if(Objects.nonNull(data.participant())){
                    participant.setParticipant(data.participant());
                }else{
                    participant.setParticipant(user.getNames() + " " + user.getSurnames());
                }
            }else {
                if (Objects.nonNull(data.participant())) {
                    participant.setAttendance(true);
                    participant.setAttendanceDate(Calendar.getInstance());
                    participant.setParticipant(data.participant());
                }else{
                    return null;
                }
            }
        }else{
            participant.setAttendance(false);
            participant.setAttendanceDate(null);
            participant.setParticipant(null);
            participant.setDeviceId(null);
        }

        return convocationParticipantRepository.save(participant);
    }

    @Override
    public ConvocationParticipant updateAttendanceParticipant(Long id, ConvocationAttendanceData data) {
        ConvocationParticipant participant = findParticipantById(id);
        if(participant.getConvocation().getFinalized()){
            return null;
        }
        if(Objects.nonNull(data.deviceId())){
            ConvocationParticipant participantByDeviceId = convocationParticipantRepository.findFirstByConvocationIdAndDeviceId(participant.getConvocation().getId(), data.deviceId()).orElse(null);
            if(Objects.nonNull(participantByDeviceId)){
                if (!Objects.equals(participantByDeviceId.getResidence().getUser().getId(), participant.getResidence().getUser().getId())){
                    return null;
                }
            }
        }

        Calendar now = Calendar.getInstance();
        if (now.before(participant.getConvocation().getAttendanceDeadline()) && now.after(participant.getConvocation().getDate())){
            Residence residence = residenceService.findById(data.residence());
            User user = residence.getUser();
            if (data.attendance()){
                if(Objects.nonNull(user)){
                    participant.setAttendance(true);
                    participant.setDeviceId(data.deviceId());
                    participant.setAttendanceDate(Calendar.getInstance());
                    if(Objects.nonNull(data.participant())){
                        participant.setParticipant(data.participant());
                    }else{
                        participant.setParticipant(user.getNames() + " " + user.getSurnames());
                    }
                }else {
                    if (Objects.nonNull(data.participant())) {
                        participant.setDeviceId(data.deviceId());
                        participant.setAttendance(true);
                        participant.setAttendanceDate(Calendar.getInstance());
                        participant.setParticipant(data.participant());
                    }else{
                        return null;
                    }
                }
            }else{
                participant.setAttendance(false);
                participant.setAttendanceDate(null);
                participant.setParticipant(null);
                participant.setDeviceId(null);
            }

            return convocationParticipantRepository.save(participant);
        }else{
            return null;
        }

    }

    @Override
    public ConvocationParticipant findParticipantById(Long id) {
        return convocationParticipantRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ConvocationParticipant findByConvocationIdAndResidenceUserId(Long id, Long userId) {
        return convocationParticipantRepository.findFirstByConvocationIdAndResidenceUserId(id, userId).orElse(null);
    }



    @Override
    public List<ConvocationParticipant> findAllParticipants(Long id) {
        return convocationParticipantRepository.findAllByConvocationOrderByResidenceNumber(findById(id));
    }

    @Override
    public Convocation findFirstByDateBetweenAndFinalizedIsFalse() {
        Calendar now = Calendar.getInstance();
        Calendar start = (Calendar) now.clone();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Calendar end = (Calendar) now.clone();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        return convocationRepository.findFirstByDateBetweenAndFinalizedIsFalseAndTypeOrType(start, end, ConvocationType.ASSEMBLY_EXTRAORDINARY, ConvocationType.ASSEMBLY_ORDINARY);
    }

    @Override
    public List<ConvocationParticipant> findAllByConvocationIdAndResidenceUserId(Long id, Long userId) {
        return convocationParticipantRepository.findAllByConvocationIdAndResidenceUserId(id, userId);
    }

    private String generateCode(ConvocationType type) {
        return "CONV-" + type.name().charAt(0) + "-" + String.format("%06d", convocationRepository.count() + 1);
    }
}
