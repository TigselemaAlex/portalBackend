package com.example.portalbackend.service.generator;

import com.example.portalbackend.api.dto.request.passage.PassageCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceCreateData;
import com.example.portalbackend.api.dto.request.user.UserCreateData;
import com.example.portalbackend.domain.entity.Passage;
import com.example.portalbackend.domain.entity.Role;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.domain.repository.PassageRepository;
import com.example.portalbackend.domain.repository.RoleRepository;
import com.example.portalbackend.service.impl.PassageService;
import com.example.portalbackend.service.impl.ResidenceService;
import com.example.portalbackend.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataGeneratorService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PassageService passageService;

    private final ResidenceService residenceService;

    public DataGeneratorService(RoleRepository roleRepository, UserService userService, PassageService passageService, ResidenceService residenceService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passageService = passageService;
        this.residenceService = residenceService;
    }

    public void generateRoles() {
        System.out.println("Generating roles");
        List<Role> roles = List.of(
                Role.builder().name("ADMIN").description("Administrador del sistema").build(),
                Role.builder().name("PRESIDENT").description("Presidente del conjunto habitacional").build(),
                Role.builder().name("VICE_PRESIDENT").description("Vicepresidente del conjunto habitacional").build(),
                Role.builder().name("SECRETARY").description("Secretario del conjunto habitacional").build(),
                Role.builder().name("TREASURER").description("Tesorero del conjunto habitacional").build(),
                Role.builder().name("OWNER").description("Propietario del conjunto habitacional").build(),
                Role.builder().name("TENANT").description("Inquilino del conjunto habitacional").build()

        );
        roleRepository.saveAll(roles);
    }

    public void generateUsers() {
        System.out.println("Generating users");
        List<UserCreateData> users = List.of(
            new UserCreateData(
                    "Admin Nombre",
                    "Admin Apellido",
                    "alextp.dev@gmail.com",
                    "0967205537",
                    "1803834371",
                    List.of(1L,2L,3L,4L,5L,6L,7L)),
            new UserCreateData(
                    "Presidente nombre",
                    "Presidente apellido",
                    "presidente@correo.com",
                    "0967205537",
                    

                    "1234567891",
                    List.of(2L,6L)),
            new UserCreateData(
                    "Vicepresidente nombre",
                    "Vicepresidente apellido",
                    "vicepresidente@correo.com",
                    "0967205537",
                    

                    "1234567892",
                    List.of(3L,6L)),
            new UserCreateData(
                    "Secretario nombre",
                    "Secretario apellido",
                    "secretario@correo.com",
                    "0967205537",
                    

                    "1234567893",
                    List.of(4L,6L)),
            new UserCreateData(
                    "Tesorero nombre",
                    "Tesorero apellido",
                    "tesorero@correo.com",
                    "0967205537",
                    

                    "1234567894",
                    List.of(5L,6L)),
            new UserCreateData(
                    "Propietario1 nombre",
                    "Propietario1 apellido",
                    "propietario1@correo.com",
                    "0967205537",
                    

                    "1234567801",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario2 nombre",
                    "Propietario2 apellido",
                    "propietario2@correo.com",
                    "0967205537",
                    

                    "1234567802",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario3 nombre",
                    "Propietario3 apellido",
                    "propietario3@correo.com",
                    "0967205537",
                    

                    "1234567803",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario4 nombre",
                    "Propietario4 apellido",
                    "propietario4@correo.com",
                    "0967205537",
                    

                    "1234567804",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario5 nombre",
                    "Propietario5 apellido",
                    "propietario5@correo.com",
                    "0967205537",
                    

                    "1234567805",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario6 nombre",
                    "Propietario6 apellido",
                    "propietario6@correo.com",
                    "0967205537",
                    

                    "1234567806",
                    List.of(6L)),
            new UserCreateData(
                    "Propietario7 nombre",
                    "Propietario7 apellido",
                    "propietario7@correo.com",
                    "0967205537",
                    

                    "1234567807",
                    List.of(6L)),
            new UserCreateData(
                    "Inquilino1 nombre",
                    "Inquilino1 apellido",
                    "inquilino1@correo.com",
                    "0967205537",
                    

                    "1234567811",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino2 nombre",
                    "Inquilino2 apellido",
                    "inquilino2@correo.com",
                    "0967205537",
                    

                    "1234567812",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino3 nombre",
                    "Inquilino3 apellido",
                    "inquilino3@correo.com",
                    "0967205537",
                    

                    "1234567813",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino4 nombre",
                    "Inquilino4 apellido",
                    "inquilino4@correo.com",
                    "0967205537",
                    

                    "1234567814",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino5 nombre",
                    "Inquilino5 apellido",
                    "inquilino5@correo.com",
                    "0967205537",
                    

                    "1234567815",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino6 nombre",
                    "Inquilino6 apellido",
                    "inquilino6@correo.com",
                    "0967205537",
                    

                    "1234567816",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino7 nombre",
                    "Inquilino7 apellido",
                    "inquilino7@correo.com",
                    "0967205537",
                    

                    "1234567817",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino8 nombre",
                    "Inquilino8 apellido",
                    "inquilino8@correo.com",
                    "0967205537",
                    

                    "1234567818",
                    List.of(7L)),
            new UserCreateData(
                    "Inquilino9 nombre",
                    "Inquilino9 apellido",
                    "inquilino9@correo.com",
                    "0967205537",
                    

                    "12345678",
                    List.of(7L))
        );

        users.forEach(userService::create);
    }

    public void generatePassages() {
        System.out.println("Generating passages");
        List<PassageCreateData> createData = List.of(
                new PassageCreateData("Calle Caracas"),
                new PassageCreateData("Pasaje Maracay"),
                new PassageCreateData("Calle Corrientes"),
                new PassageCreateData("Pasaje Osorno"),
                new PassageCreateData("Pasaje Ica"),
                new PassageCreateData("Puerto Principe"),
                new PassageCreateData("Calle Iquique"),
                new PassageCreateData("Pasaje Corrientes"),
                new PassageCreateData("Pasaje Rosario"),
                new PassageCreateData("Pasaje San Estanislao"),
                new PassageCreateData("Cale Tucumán"),
                new PassageCreateData("Calle Concepción")
        );
        createData.forEach(passageService::create);
    }

    public void generateResidences() {
        System.out.println("Generating residences");
        Passage calleCaracas = passageService.findByName("Calle Caracas");
        Passage pasajeMaracay = passageService.findByName("Pasaje Maracay");
        Passage calleCorrientes = passageService.findByName("Calle Corrientes");
        Passage pasajeOsorno = passageService.findByName("Pasaje Osorno");
        Passage pasajeIca = passageService.findByName("Pasaje Ica");
        Passage puertoPrincipe = passageService.findByName("Puerto Principe");
        Passage calleIquique = passageService.findByName("Calle Iquique");
        Passage pasajeCorrientes = passageService.findByName("Pasaje Corrientes");
        Passage pasajeRosario = passageService.findByName("Pasaje Rosario");
        Passage pasajeSanEstanislao = passageService.findByName("Pasaje San Estanislao");
        Passage calleTucuman = passageService.findByName("Cale Tucumán");
        Passage calleConcepcion = passageService.findByName("Calle Concepción");

        List<ResidenceCreateData> createData = new ArrayList<>();

        for (int i = 1; i <= 18; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleCaracas.getId()));
        }
        for (int i = 19; i <= 53; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeMaracay.getId()));
        }
        for (int i = 54; i <= 61; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleCorrientes.getId()));
        }
        for (int i = 62; i <= 79; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeOsorno.getId()));
        }
        for (int i = 80; i <= 97; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeIca.getId()));
        }
        for (int i = 107; i <= 116; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeIca.getId()));
        }
        for (int i = 98; i <= 106; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,puertoPrincipe.getId()));
        }
        for (int i = 117; i <= 126; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,puertoPrincipe.getId()));
        }
        for (int i = 127; i <= 153; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleIquique.getId()));
        }
        for (int i = 154; i <= 183; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeCorrientes.getId()));
        }
        for (int i = 184; i <= 216; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeRosario.getId()));
        }
        for (int i = 217; i <= 253; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,pasajeSanEstanislao.getId()));
        }
        for (int i = 254; i <= 288; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleTucuman.getId()));
        }
        for (int i = 289; i <= 303; i++) {
            createData.add(new ResidenceCreateData(String.valueOf(i), null,calleConcepcion.getId()));
        }
        createData.forEach(residenceService::save);
    }
}
