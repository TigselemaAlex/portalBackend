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

@Service
@Transactional(readOnly = false)
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Role> findAll(String name, String description, Pageable pageable) {
        return roleRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name,description,pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return null;
    }
    @Override
    public Role update(RoleUpdateData role, Long id) {
        Role roleToUpdate = roleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        roleToUpdate.setDescription(role.description());
        return roleRepository.save(roleToUpdate);
    }

}
