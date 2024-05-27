package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Parking;
import com.example.portalbackend.domain.entity.ParkingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findByGroupOrderById(ParkingGroup group);
    Long countByGroupTypeId(Long id);
    Long countByGroupTypeIdAndResidenceIsNotNull(Long id);
}
