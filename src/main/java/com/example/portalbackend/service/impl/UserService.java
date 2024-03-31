package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.api.dto.request.user.UserUpdateData;
import com.example.portalbackend.api.dto.request.user.UserUpdatePasswordData;
import com.example.portalbackend.domain.entity.AuthRole;
import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.domain.repository.UserRepository;
import com.example.portalbackend.domain.repository.UserRoleRepository;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.user.UserUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
@Service
@Transactional
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(String names, String surnames, String dni, Pageable pageable) {
        return userRepository.findAllByNamesContainingIgnoreCaseOrSurnamesContainingIgnoreCaseOrDniContainingIgnoreCase(names, surnames, dni, pageable);
    }

    @Override
    public List<User> findAllActive(String names, String surnames, String dni) {
        return userRepository.findAllActive(names, surnames, dni);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User update(UserUpdateData user, Long id) {
        User userToUpdate = findById(id);
        userToUpdate.setNames(user.names());
        userToUpdate.setSurnames(user.surnames());
        userToUpdate.setDni(user.dni());
        userToUpdate.setActive(user.active());
        userToUpdate.setPhone(user.phone());
        userToUpdate.setEmail(user.email());
        List<Role> roles = roleRepository.findAllById(user.roles());
        userRoleRepository.deleteAllByUser(userToUpdate);
        List<AuthRole> authRoles = roles.stream().map(role -> AuthRole.builder().role(role).user(userToUpdate).build()).toList();
        userToUpdate.setAuthRoles(new HashSet<>(authRoles));
        return userRepository.save(userToUpdate);
    }

    @Override
    public User create(UserCreateData user) {
        List<Role> roles = roleRepository.findAllById(user.roles());
        User userToCreate = User.builder()
                .names(user.names())
                .surnames(user.surnames())
                .dni(user.dni())
                .phone(user.phone())
                .email(user.email())
                .password(passwordEncoder.encode(UserUtil.generatePassword()))
                .build();
        userToCreate = userRepository.save(userToCreate);
        User finalUserToCreate = userToCreate;
        List<AuthRole> authRoles = roles.stream().map(role -> AuthRole.builder().role(role).user(finalUserToCreate).build()).toList();
        finalUserToCreate.setAuthRoles(new HashSet<>(authRoles));
        userRoleRepository.saveAllAndFlush(authRoles);
        return finalUserToCreate;
    }

    @Override
    public void delete(Long id) {
        User userToDelete = findById(id);
        userRepository.delete(userToDelete);
    }

    @Override
    public void reactivate(Long id) {
        User userToReactivate = findById(id);
        userToReactivate.setActive(true);
        userRepository.save(userToReactivate);
    }

    @Override
    public User recoverPassword(String dni, String password) {
        User userToRecover = userRepository.findByDni(dni).orElseThrow(EntityNotFoundException::new);
        userToRecover.setPassword(passwordEncoder.encode(password));
        return userRepository.save(userToRecover);
    }

    @Override
    public User updatePassword(Long id, UserUpdatePasswordData data) {
        User userToUpdate = findById(id);
        userToUpdate.setPassword(passwordEncoder.encode(data.password()));
        return userRepository.save(userToUpdate);
    }

    @Override
    public User findPresident() {
        return userRepository.findFirstByAuthRolesRoleNameOrderByUpdatedAtDesc("PRESIDENT").orElseThrow(EntityNotFoundException::new);
    }
}
