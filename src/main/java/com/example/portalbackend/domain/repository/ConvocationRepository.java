package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.util.enumerate.ConvocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Calendar;

public interface ConvocationRepository extends JpaRepository<Convocation, Long>, JpaSpecificationExecutor<Convocation> {
    Convocation findFirstByDateBetweenAndFinalizedIsFalseAndType(Calendar start, Calendar end, ConvocationType type);
}
