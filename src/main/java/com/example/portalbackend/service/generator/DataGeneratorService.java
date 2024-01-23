package com.example.portalbackend.service.generator;

import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataGeneratorService {

    private final RoleRepository roleRepository;

    public DataGeneratorService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void generateRoles() {
        System.out.println("Generating roles");
        List<Role> roles = List.of(
                Role.builder().name("ADMIN").description("Administrador del sistema").build(),
                Role.builder().name("PRESIDENT").description("Presidente del conjunto habitacional").build(),
                Role.builder().name("VICE_PRESIDENT").description("Vicepresidente del conjunto habitacional").build(),
                Role.builder().name("SECRETARY").description("Secretario del conjunto habitacional").build(),
                Role.builder().name("TREASURER").description("Tesorero del conjunto habitacional").build(),
                Role.builder().name("RESIDENT").description("Residente del conjunto habitacional").build()
        );
        roleRepository.saveAll(roles);
    }
}
