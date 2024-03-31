package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.role.RoleUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.role.RoleResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.service.spec.IRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleUseCase extends AbstractUseCase {
    private final IRoleService roleService;

    protected RoleUseCase(CustomResponseBuilder customResponseBuilder, IRoleService roleService) {
        super(customResponseBuilder);
        this.roleService = roleService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(final String search, final Pageable pageable) {
        Page<RoleResponse> responses = roleService.findAll(search, search, pageable).map(RoleResponse::new);
        PageResponse response = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de roles obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllActive() {
        List<Role> roles = roleService.findAllActive();
        List<RoleResponse> responses = roles.stream().map(RoleResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de roles activos obtenido exitosamente", responses);
    }

    public ResponseEntity<CustomResponse<?>> update(final RoleUpdateData role, final Long id){
        Role roleFromDb = roleService.update(role, id);
        RoleResponse response = new RoleResponse(roleFromDb);
        return customResponseBuilder.build(HttpStatus.OK, "Rol actualizado exitosamente", response);
    }
}
