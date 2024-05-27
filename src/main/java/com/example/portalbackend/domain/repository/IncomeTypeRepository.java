package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long>{


    List<IncomeType> findAllByActiveIsTrueOrderById();

    List<IncomeType> findAllByActiveIsTrueAndPeriod(IncomeTypePeriod period);
}
