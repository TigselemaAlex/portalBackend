package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.entity.Residence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResidenceRepository extends JpaRepository<Residence, Long> {
    Page<Residence> findAllByNumberContainingIgnoreCaseOrUserNamesContainingIgnoreCaseOrUserSurnamesContainingIgnoreCase(String number, String names,String surnames ,Pageable pageable);

    long countResidenceByUserIsNull();

    long countResidenceByUserIsNotNull();

    long countResidenceByUserIsNotNullAndPassageName(String passageName);
    long countResidenceByUserIsNotNullAndPassage(Passage passage);

}
