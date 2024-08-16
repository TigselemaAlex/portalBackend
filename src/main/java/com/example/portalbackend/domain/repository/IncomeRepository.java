package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.util.enumerate.IncomeTypePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long>, JpaSpecificationExecutor<Income> {

Optional<Income> findFirstByActiveIsTrueAndResidenceAndTypeOrderByPaidUntilDesc(Residence residence, IncomeType type);


    Optional<Income> findFirstByOrderByIdDesc();

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.active = true")
    Double sumTotalIncomes();

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.active = true AND (i.paidDate >= ?1 AND i.paidDate <= ?2)")
    Double sumTotalIncomesByFromAndTo(Calendar from, Calendar to);

    List<Income> findAllByActiveIsTrueAndPaidDateBetweenOrderById(Calendar from, Calendar to);

    List<Income> findTop10ByActiveIsTrueAndResidenceAndTypePeriodOrderByPaidDateDesc(Residence residence, IncomeTypePeriod period);

    Optional<Income> findFirstByActiveIsTrueAndResidenceAndTypeAndParkingOrderByPaidUntilDesc(Residence residence, IncomeType type, Parking parking);

    Optional<Income> findFirstByActiveIsTrueAndParkingAndTypeOrderByPaidUntilDesc(Parking parking, IncomeType type);

}
