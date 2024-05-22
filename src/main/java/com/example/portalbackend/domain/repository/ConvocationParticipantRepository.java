package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import com.example.portalbackend.domain.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConvocationParticipantRepository extends JpaRepository<ConvocationParticipant, Long>{
    Long countByConvocationIdAndAttendanceIsFalse(Long id);
    List<ConvocationParticipant> findAllByConvocationOrderByResidenceNumber(Convocation convocation);

    List<ConvocationParticipant> findAllByConvocationIdAndResidenceUserId(Long id, Long userId);

    Optional<ConvocationParticipant> findFirstByConvocationIdAndResidenceUserId(Long id, Long userId);

    Optional<ConvocationParticipant> findFirstByConvocationIdAndDeviceId(Long id, String deviceId);
}
