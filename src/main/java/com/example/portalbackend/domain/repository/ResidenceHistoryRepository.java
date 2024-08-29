package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.entity.ResidenceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidenceHistoryRepository extends JpaRepository<ResidenceHistory, Long> {
    Optional<ResidenceHistory> findFirstByResidenceAndFinishDateIsNull(Residence residence);
    List<ResidenceHistory> findAllByResidence(Residence residence);
}
