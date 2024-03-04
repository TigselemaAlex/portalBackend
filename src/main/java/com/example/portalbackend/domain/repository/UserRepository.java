package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findAllByNamesContainingIgnoreCaseOrSurnamesContainingIgnoreCaseOrDniContainingIgnoreCase(String names, String surnames, String dni, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.active = true AND (u.names LIKE %:names% OR u.surnames LIKE %:surnames% OR u.dni LIKE %:dni%)")
    List<User> findAllActive(String names, String surnames, String dni);

    List<User> findAllByActiveIsFalse();

}
