package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.domain.entity.ParkingGroup;
import com.example.portalbackend.domain.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findByGroupOrderById(ParkingGroup group);
    Long countByGroupTypeId(Long id);
    Long countByGroupTypeIdAndResidenceIsNotNull(Long id);
    Optional<Parking> findByIdAndResidence(Long id, Residence residence);
    List<Parking> findAllByResidence(Residence residence);
}
