package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.util.enumerate.PaidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long>, JpaSpecificationExecutor<Penalty> {

    Optional<Penalty> findFirstByOrderByIdDesc();

    @Query("SELECT SUM(p.amount) FROM Penalty p WHERE p.active = true")
    Double sumTotalPenalties();

    @Query("SELECT SUM(p.amount) FROM Penalty p WHERE p.active = true AND p.status = 'PAID' AND (p.paidDate >= ?1 AND p.paidDate <= ?2)")
    Double  sumTotalPenaltiesByFromAndTo(Calendar from, Calendar to);

    List<Penalty> findAllByActiveIsTrueAndPaidDateBetweenAndStatusOrderById(Calendar from, Calendar to, PaidStatus status);

    List<Penalty> findTop10ByActiveIsTrueAndStatusAndResidenceOrderByPaidDateDesc(PaidStatus status, Residence residence);

    List<Penalty> findAllByActiveIsTrueAndStatusAndResidenceOrderByIssueDateDesc(PaidStatus status, Residence residence);

    @Query("SELECT SUM(p.amount) FROM Penalty p WHERE p.active = true AND p.status = 'UNPAID' AND p.residence = ?1")
    Double sumTotalPenaltiesByResidence(Residence residence);
}
