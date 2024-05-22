package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IncomeRepository extends JpaRepository<Income, Long>, JpaSpecificationExecutor<Income> {


}
