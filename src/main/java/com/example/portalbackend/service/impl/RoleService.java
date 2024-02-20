package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.role.RoleUpdateData;
import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.service.spec.IRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional()
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(String name, String description, Pageable pageable) {
        return roleRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase
                        (name,description,pageable);
    }

    @Override
    public List<Role> findAllActive() {
        return roleRepository.findAllByActiveIsTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
    @Override
    public Role update(RoleUpdateData role, Long id) {
        Role roleToUpdate = findById(id);
        roleToUpdate.setDescription(role.description());
        roleToUpdate.setActive(role.active());

        return roleRepository.save(roleToUpdate);
    }


}
