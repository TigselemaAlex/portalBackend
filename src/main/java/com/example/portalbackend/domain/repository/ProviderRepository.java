package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findAllByOrderById();
    List<Provider> findAllByActiveIsTrueOrderById();
}
