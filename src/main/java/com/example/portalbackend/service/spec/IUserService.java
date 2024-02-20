package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.api.dto.request.user.UserUpdateData;
import com.example.portalbackend.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAll(String names, String surnames, String dni, Pageable pageable);

    User findById(Long id);

    User update(UserUpdateData user, Long id);

    User create(UserCreateData user);

    void delete(Long id);

    void reactivate(Long id);
}
