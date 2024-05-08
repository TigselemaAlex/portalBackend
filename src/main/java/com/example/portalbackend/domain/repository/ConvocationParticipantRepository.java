package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConvocationParticipantRepository extends JpaRepository<ConvocationParticipant, Long>{
    Long countByConvocationIdAndAttendanceIsFalse(Long id);
    List<ConvocationParticipant> findAllByConvocationOrderByResidenceNumber(Convocation convocation);

    List<ConvocationParticipant> findAllByConvocationIdAndResidenceUserId(Long id, Long userId);
}
