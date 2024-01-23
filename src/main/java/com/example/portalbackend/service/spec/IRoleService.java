package com.example.portalbackend.service.spec;

import com.example.portalbackend.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Role create(Role role);
    Page<Role> findAll(Pageable pageable);
    Role findById(Long id);
    Role update(Role role, Long id);
    void delete(Long id);
}
