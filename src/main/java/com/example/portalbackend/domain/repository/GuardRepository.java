package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Guard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuardRepository extends JpaRepository<Guard, Long> {

    @Query("SELECT g FROM Guard g WHERE g.active = true AND " +
            "(lower(g.fullName) LIKE lower(concat('%',:search,'%')) OR " +
            "lower(g.dni) LIKE lower(concat('%',:search,'%')))")
    Page<Guard> findAllByActiveIsTrue(String search, Pageable pageable);
    Page<Guard> findAllGuardsByFullNameContainingIgnoreCaseOrDniContainingIgnoreCase(String fullName, String dni, Pageable pageable);
}
