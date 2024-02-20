package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findAllByNamesContainingIgnoreCaseOrSurnamesContainingIgnoreCaseOrDniContainingIgnoreCase(String names, String surnames, String dni, Pageable pageable);

}
