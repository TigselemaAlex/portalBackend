package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.parking.ParkingUpdateData;
import com.example.portalbackend.api.dto.request.parking_type.ParkingTypeUpdateData;
import com.example.portalbackend.api.dto.response.parking.ParkingResponse;
import com.example.portalbackend.api.dto.response.parking_type.ParkingTypeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.service.spec.IParkingService;
import com.example.portalbackend.service.spec.IParkingTypeService;
import com.example.portalbackend.service.spec.IResidenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParkingUseCase extends AbstractUseCase{

    private final IParkingService parkingService;
    private final IParkingTypeService parkingTypeService;
    private final IResidenceService residenceService;
    protected ParkingUseCase(CustomResponseBuilder customResponseBuilder, IParkingService parkingService, IParkingTypeService parkingTypeService, IResidenceService residenceService) {
        super(customResponseBuilder);
        this.parkingService = parkingService;
        this.parkingTypeService = parkingTypeService;
        this.residenceService = residenceService;
    }

    public ResponseEntity<CustomResponse<?>> findByGroup(Long groupId) {
        List<ParkingResponse> responses = parkingService.findByGroup(groupId)
                .stream().map(ParkingResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de parqueaderos por grupo", responses);
    }

    public ResponseEntity<CustomResponse<?>> update(final Long id, final ParkingUpdateData data) {
        ParkingResponse response = new ParkingResponse(parkingService.update(data, id));
        return customResponseBuilder.build(HttpStatus.OK, "Parqueadero actualizado", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllParkingsType() {
        List<ParkingTypeResponse> responses = parkingTypeService.findAll()
                .stream().map(ParkingTypeResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de parqueaderos", responses);
    }

    public ResponseEntity<CustomResponse<?>> updateParkingType(final Long id, final ParkingTypeUpdateData data) {
        ParkingTypeResponse response = new ParkingTypeResponse(parkingTypeService.update(data, id));
        return customResponseBuilder.build(HttpStatus.OK, "Parqueadero actualizado", response);
    }

    public ResponseEntity<CustomResponse<?>> findAllByResidence(Long residence){
        List<ParkingResponse> responses = parkingService.findAllByResidence(residenceService.findById(residence))
                .stream().map(ParkingResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Listado de parqueaderos por residencia", responses);
    }

}
