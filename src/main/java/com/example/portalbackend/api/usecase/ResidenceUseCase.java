package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.residence.ResidenceCreateData;
import com.example.portalbackend.api.dto.request.residence.ResidenceUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.residence.ResidenceResponse;
import com.example.portalbackend.api.dto.response.residence_history.ResidenceHistoryResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.util.number.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResidenceUseCase extends AbstractUseCase {
    private final IResidenceService residenceService;
    protected ResidenceUseCase(CustomResponseBuilder customResponseBuilder, IResidenceService residenceService) {
        super(customResponseBuilder);
        this.residenceService = residenceService;
    }

    public ResponseEntity<CustomResponse<?>> create(ResidenceCreateData residence){
        if (!NumberUtils.isNumeric(residence.number()))
            return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "El número de residencia debe ser un número", null);
        Residence createdResidence = residenceService.save(residence);
        ResidenceResponse response = new ResidenceResponse(createdResidence);
        return customResponseBuilder.build(HttpStatus.CREATED, "Residencia creada exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, ResidenceUpdateData residence){
        Residence updatedResidence = residenceService.update(residence, id);
        ResidenceResponse response = new ResidenceResponse(updatedResidence);
        return customResponseBuilder.build(HttpStatus.OK, "Residencia actualizada exitosamente", response);
    }


    public ResponseEntity<CustomResponse<?>> findAll(String number, Pageable pageable){
        Page<ResidenceResponse> residences = residenceService.findAll(number, pageable).map(ResidenceResponse::new);
        PageResponse response = new PageResponse(residences);
        return customResponseBuilder.build(HttpStatus.OK, "Residencia obtenida exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findById(Long id){
        Residence residence = residenceService.findById(id);
        ResidenceResponse response = new ResidenceResponse(residence);
        return customResponseBuilder.build(HttpStatus.OK, "Residencia obtenida exitosamente", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllWithoutPagination(){
        List<ResidenceResponse> responses =  residenceService.findAllNumbers().stream().map(ResidenceResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de todas las residencias", responses);
    }

    public ResponseEntity<CustomResponse<?>> findResidenceHistory(Long id){
        List<ResidenceHistoryResponse> responses = residenceService.findResidenceHistory(id).stream().map(ResidenceHistoryResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de historial de residencia", responses);
    }


}
