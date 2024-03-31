package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.SocialEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;

public interface SocialEventRepository extends JpaRepository<SocialEvent, Long> {

    Page<SocialEvent> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<SocialEvent> findAllByDateBetweenAndTitleContainingIgnoreCase(Calendar from, Calendar to, String title, Pageable pageable);

    @Query("SELECT s FROM SocialEvent s WHERE s.date >= :from AND  lower(s.title) LIKE lower(concat('%',:title,'%'))")
    Page<SocialEvent> findAllByTitleAndDateAfter(String title, Calendar from, Pageable pageable);

    @Query("SELECT s FROM SocialEvent s WHERE s.date <= :to AND lower(s.title) LIKE lower(concat('%',:title,'%'))")
    Page<SocialEvent> findAllByTitleAndDateBefore(String title, Calendar to, Pageable pageable);

}
