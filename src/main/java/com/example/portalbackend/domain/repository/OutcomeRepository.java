package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface OutcomeRepository extends JpaRepository<Outcome, Long>, JpaSpecificationExecutor<Outcome> {

    Optional<Outcome> findFirstByOrderByIdDesc();

    @Query("SELECT SUM(o.amount) FROM Outcome o WHERE o.active = true")
    Double sumTotalOutcomes();

    @Query("SELECT SUM(o.amount) FROM Outcome o WHERE o.active = true AND (o.paidDate >= ?1 AND o.paidDate <= ?2)")
    Double sumTotalOutcomesByFromAndTo(Calendar from, Calendar to);

    List<Outcome> findAllByActiveIsTrueAndPaidDateBetweenOrderById(Calendar from, Calendar to);
}
