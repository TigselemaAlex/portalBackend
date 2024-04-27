package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Convocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConvocationRepository extends JpaRepository<Convocation, Long>, JpaSpecificationExecutor<Convocation> {
}
