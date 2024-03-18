package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.ParkingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingTypeRepository extends JpaRepository<ParkingType, Long> {
}
