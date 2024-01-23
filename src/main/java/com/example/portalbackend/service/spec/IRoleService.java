package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.role.RoleUpdateData;
import com.example.portalbackend.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Page<Role> findAll(String name, String description,Pageable pageable);
    Role findById(Long id);
    Role update(RoleUpdateData role, Long id);

}
