package com.example.portalbackend.api.controller;

import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.api.dto.request.user.UserUpdateData;
import com.example.portalbackend.api.dto.request.user.UserUpdatePasswordData;
import com.example.portalbackend.api.usecase.UserUseCase;
import com.example.portalbackend.common.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protected/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> findAll(
            @RequestParam(defaultValue = "") String search,
            @PageableDefault(
                    size = 10,
                    sort = "updatedAt",
                    direction = Sort.Direction.DESC)
            Pageable pageable

    ) {
        return userUseCase.findAll(search, pageable);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<CustomResponse<?>> findAllActive(
            @RequestParam(defaultValue = "") String search
    ) {
        return userUseCase.findAllActive(search);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<?>> findById(@PathVariable Long id) {
        return userUseCase.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<?>> update(@Valid @RequestBody UserUpdateData user, @PathVariable Long id) {
        return userUseCase.update(user, id);
    }

    @PutMapping(value = "/{id}/update-password")
    public ResponseEntity<CustomResponse<?>> updatePassword(@Valid @RequestBody UserUpdatePasswordData user, @PathVariable Long id) {
        return userUseCase.updatePassword(id, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CustomResponse<?>> create(@Valid @RequestBody UserCreateData user) {
        return userUseCase.create(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomResponse<?>> delete(@PathVariable Long id) {
        return userUseCase.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}/reactivate")
    public ResponseEntity<CustomResponse<?>> reactivate(@PathVariable Long id) {
        return userUseCase.reactivate(id);
    }


    @PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_VICEPRESIDENT', 'ROLE_TREASURER', 'ROLE_ADMIN', 'ROLE_SECRETARY')")
    @GetMapping(value = "/president")
    public ResponseEntity<CustomResponse<?>> findPresident(){
        return userUseCase.findPresident();
    }

}
