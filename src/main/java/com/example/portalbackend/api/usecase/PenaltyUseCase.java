package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.penalty.PenaltyCreateData;
import com.example.portalbackend.api.dto.request.penalty.PenaltyUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.api.dto.response.penalty.PenaltyResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.PenaltyType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.*;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.enumerate.PaidStatus;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

@Component
public class PenaltyUseCase extends AbstractUseCase{

    private final IPenaltyService penaltyService;
    private final IPenaltyTypeService penaltyTypeService;
    private final IResidenceService residenceService;
    private final IMailService mailService;
    private final IPushNotificationService pushNotificationService;

    protected PenaltyUseCase(CustomResponseBuilder customResponseBuilder, IPenaltyService penaltyService, IPenaltyTypeService penaltyTypeService, IResidenceService residenceService, IMailService mailService, IPushNotificationService pushNotificationService) {
        super(customResponseBuilder);
        this.penaltyService = penaltyService;
        this.penaltyTypeService = penaltyTypeService;
        this.residenceService = residenceService;
        this.mailService = mailService;
        this.pushNotificationService = pushNotificationService;
    }

    public ResponseEntity<CustomResponse<?>>findAllActive(Long type, Long from, Long to, Long residence, PaidStatus status, Pageable pageable) {

        PenaltyType penaltyType = null == type ? null : penaltyTypeService.findById(type);
        Residence residenceObj = null == residence ? null : residenceService.findById(residence);
        Page<PenaltyResponse> responses = penaltyService.findAllActive(penaltyType, CalendarUtil.getCalendar(from), CalendarUtil.getCalendar(to), residenceObj, status, pageable)
                .map(PenaltyResponse::new);
        PageResponse pageResponse = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de multas activas", pageResponse);
    }


    public ResponseEntity<CustomResponse<?>> save(PenaltyCreateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Penalty penalty = penaltyService.save(data);
        PenaltyResponse response = new PenaltyResponse(penalty);
        if(penalty.getStatus().equals(PaidStatus.UNPAID)){
            HashMap<String, String> pushData = new HashMap<>();
            pushData.put("id", response.id().toString());
            pushNotificationService.sendPushNotification(response.residence().user().id(), response.type().name(), "Se ha generado una multa, revise la sección de Mi Hogar para mas información", pushData);
        }
        else{
            sendEmail(penalty);
        }
        return customResponseBuilder.build(HttpStatus.OK, "Multa creada", response);
    }

    public ResponseEntity<CustomResponse<?>> update(Long id, PenaltyUpdateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Penalty penalty = penaltyService.update(id, data);
        PenaltyResponse response = new PenaltyResponse(penalty);
        if(penalty.getStatus().equals(PaidStatus.PAID)){
            sendEmail(penalty);
        }
        return customResponseBuilder.build(HttpStatus.OK, "Multa actualizada", response);
    }

    public ResponseEntity<CustomResponse<?>> delete(Long id) {
        penaltyService.deleteById(id);
        return customResponseBuilder.build(HttpStatus.OK, "Multa eliminada", null);
    }

    private MailResponse sendEmail(Penalty penalty) throws FirebaseMessagingException {
        MailResponse response = null;
        if(penalty.getResidence().getUser() != null){
            response = mailService.sendMailPenalty(penalty.getResidence().getUser(), penalty);
            HashMap<String, String> pushData = new HashMap<>();
            pushData.put("id", penalty.getId().toString());
            pushNotificationService.sendPushNotification(penalty.getResidence().getUser().getId(), penalty.getType().getName(), "Se ha enviado a su correo electrónico el comprobante de cobro", pushData);
        }
        return response;
    }
}
