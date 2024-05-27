package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.Passage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassageRepository extends JpaRepository<Passage, Long> {
    Page<Passage> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Passage> findAllByActiveIsTrue();
    Passage findByName(String name);

}

