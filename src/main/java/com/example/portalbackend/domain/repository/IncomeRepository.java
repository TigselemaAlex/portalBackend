package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long>, JpaSpecificationExecutor<Income> {

Optional<Income> findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(Residence residence, IncomeType type);

Optional<Income> findFirstByOrderByIdDesc();
}
