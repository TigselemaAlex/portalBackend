package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.api.dto.request.user.UserUpdateData;
import com.example.portalbackend.api.dto.request.user.UserUpdatePasswordData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.api.dto.response.user.UserResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.service.spec.IMailService;
import com.example.portalbackend.service.spec.IPushNotificationService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.user.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserUseCase extends AbstractUseCase{

    private final IUserService userService;

    private final IMailService mailService;
    private final IPushNotificationService pushNotificationService;
    protected UserUseCase(CustomResponseBuilder customResponseBuilder, IUserService userService, IMailService mailService, IPushNotificationService pushNotificationService) {
        super(customResponseBuilder);
        this.userService = userService;
        this.mailService = mailService;
        this.pushNotificationService = pushNotificationService;
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
        return customResponseBuilder.build(HttpStatus.OK, "Usuario actualizado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> create(final UserCreateData user){
        User userFromDb = userService.create(user);
        UserResponse response = new UserResponse(userFromDb);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario creado exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> delete(final Long id){
        userService.delete(id);
        pushNotificationService.deleteDeviceToken(id);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario eliminado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> reactivate(final Long id){
        userService.reactivate(id);
        return customResponseBuilder.build(HttpStatus.OK, "Usuario reactivado exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> findAllActive(final String search){
        List<UserResponse> responses = userService.findAllActive(search, search, search).stream().map(UserResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de usuarios activos obtenido exitosamente", responses);
    }

    public ResponseEntity<MailResponse> recoverPassword(final String dni){
        String newPassword = UserUtil.generateRecoveryPassword();
        User userFromDb = userService.recoverPassword(dni, newPassword);
        MailResponse response = mailService.sendMail(userFromDb, newPassword);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CustomResponse<?>> updatePassword(final Long id, final UserUpdatePasswordData data){
        User userFromDb = userService.updatePassword(id, data);
        UserResponse response = new UserResponse(userFromDb);
        return customResponseBuilder.build(HttpStatus.OK, "Contraseña actualizada exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findPresident(){
        UserResponse response = new UserResponse(userService.findPresident());
        return customResponseBuilder.build(HttpStatus.OK, "Presidente obtenido exitosamente", response);
    }



}
