package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;

public interface ConvocationRepository extends JpaRepository<Convocation, Long>, JpaSpecificationExecutor<Convocation> {

    @Query("SELECT c FROM Convocation c WHERE c.date BETWEEN :start AND :end AND c.finalized = false AND  c.active = true AND (c.type = :type OR c.type = :type2)")
    Convocation findFirstByDateBetweenAndFinalizedIsFalseAndTypeOrType(Calendar start, Calendar end, ConvocationType type, ConvocationType type2);

    Long countByDateBetweenAndFinalizedIsTrueAndType(Calendar start, Calendar end, ConvocationType type);


}
