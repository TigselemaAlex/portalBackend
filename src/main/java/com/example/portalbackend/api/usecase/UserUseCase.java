package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.api.dto.request.user.UserUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.service.spec.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class UserUseCase extends AbstractUseCase{

    private final IUserService userService;
    protected UserUseCase(CustomResponseBuilder customResponseBuilder, IUserService userService) {
        super(customResponseBuilder);
        this.userService = userService;
    }

    public ResponseEntity<CustomResponse<?>> findAll(final String search, final Pageable pageable){
        Page<UserResponse> responses = userService.findAll(search, search, search, pageable).map(UserResponse::new);
        PageResponse response = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de usuarios obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findById(final Long id){
        UserResponse response = new UserResponse(userService.findById(id));
        return customResponseBuilder.build(HttpStatus.OK, "Usuario obtenido exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> update(final UserUpdateData user, final Long id){
        User userFromDb = userService.update(user, id);
        //UserResponse response = new UserResponse(userFromDb);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario actualizado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> create(final UserCreateData user){
        User userFromDb = userService.create(user);
        UserResponse response = new UserResponse(userFromDb);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> delete(final Long id){
        userService.delete(id);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario eliminado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> reactivate(final Long id){
        userService.reactivate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario reactivado exitosamente");
    }
}
