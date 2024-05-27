package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.ParkingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingGroupRepository extends JpaRepository<ParkingGroup, Long> {
}
