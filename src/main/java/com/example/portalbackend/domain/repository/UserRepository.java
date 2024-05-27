package com.example.portalbackend.domain.repository;

import com.example.portalbackend.domain.entity.User;
import io.jsonwebtoken.security.Jwks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findAllByNamesContainingIgnoreCaseOrSurnamesContainingIgnoreCaseOrDniContainingIgnoreCase(String names, String surnames, String dni, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.active = true AND " +
            "(lower(u.names) LIKE lower(concat('%',:names,'%')) OR " +
            "lower(u.surnames) LIKE lower(concat('%',:surnames,'%')) OR " +
            "lower(u.dni) LIKE lower(concat('%',:dni,'%')))")
    List<User> findAllActive(String names, String surnames, String dni);

    Optional<User> findByDni(String dni);

    Optional<User> findFirstByAuthRolesRoleNameOrderByUpdatedAtDesc(String roleName);

    long countUsersByActiveIsTrue();

    long countUsersByActiveIsFalse();

    long countUsersByAuthRolesRoleName(String roleName);


}
