package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.income.IncomeCasualCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeCasualUpdateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.*;
import com.example.portalbackend.util.calendar.CalendarUtil;
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
public class IncomeUseCase extends AbstractUseCase{

    private final IIncomeService incomeService;
    private final IIncomeTypeService incomeTypeService;
    private final IResidenceService residenceService;
    private final IMailService mailService;
    private final IPushNotificationService pushNotificationService;
    protected IncomeUseCase(CustomResponseBuilder customResponseBuilder, IIncomeService incomeService, IIncomeTypeService incomeTypeService, IResidenceService residenceService, IMailService mailService, IPushNotificationService pushNotificationService) {
        super(customResponseBuilder);
        this.incomeService = incomeService;

        this.incomeTypeService = incomeTypeService;
        this.residenceService = residenceService;
        this.mailService = mailService;
        this.pushNotificationService = pushNotificationService;
    }

    public ResponseEntity<CustomResponse<?>> getIncomesByFiltersMonthly(Long type, Long from, Long to, Long residence, Pageable pageable) {

        IncomeType incomeType = null == type ? null : incomeTypeService.findById(type);
        Residence residenceObj = null == residence ? null : residenceService.findById(residence);

        Page<IncomeResponse> responses = incomeService.getIncomesByFiltersMonthly(incomeType, CalendarUtil.getCalendar(from), CalendarUtil.getCalendar(to), residenceObj, pageable)
                .map(IncomeResponse::new);
        PageResponse pageResponse = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de ingresos mensuales", pageResponse);
    }

    public ResponseEntity<CustomResponse<?>> getIncomesByFiltersCasual(Long type, Long from, Long to, Long residence, Pageable pageable) {
        IncomeType incomeType = null == type ? null : incomeTypeService.findById(type);
        Residence residenceObj = null == residence ? null : residenceService.findById(residence);
        Page<IncomeResponse> responses = incomeService.getIncomesByFiltersCasual(incomeType, CalendarUtil.getCalendar(from), CalendarUtil.getCalendar(to), residenceObj, pageable)
                .map(IncomeResponse::new);
        PageResponse pageResponse = new PageResponse(responses);
        return customResponseBuilder.build(HttpStatus.OK, "Listado de ingresos casuales", pageResponse);
    }

    public ResponseEntity<CustomResponse<?>> saveIncomeFees(IncomeFeesCreateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Income income = incomeService.saveIncomeFees(data);
        sendEmail(income);
        return customResponseBuilder.build(HttpStatus.CREATED, "Ingreso mensual creado");
    }

    public ResponseEntity<CustomResponse<?>> updateIncomeFees(Long id, IncomeFeesUpdateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Income income = incomeService.updateIncomeFees(id, data);
        MailResponse response = sendEmail(income);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso mensual actualizado");
    }

    public ResponseEntity<CustomResponse<?>> deleteIncome(Long id) {
        incomeService.deleteIncome(id);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso eliminado");
    }

    public ResponseEntity<CustomResponse<?>> getLastByResidenceAndType(Long residence, Long incomeType, Long parking) {
        Income income = incomeService.getLastByResidenceAndTypeAndParking(residence, incomeType, parking);
        if (income == null) {
            return customResponseBuilder.build(HttpStatus.NOT_FOUND, "Ingreso no encontrado");
        }
        Calendar nextMonth = (Calendar) income.getPaidUntil().clone();
        nextMonth.add(Calendar.MONTH, 1);
        income.setPaidUntil(nextMonth);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso obtenido", new IncomeResponse(income));
    }

    public ResponseEntity<CustomResponse<?>> saveIncomeCasual(IncomeCasualCreateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Income income = incomeService.saveIncomeCasual(data);
        MailResponse response = sendEmail(income);
        return customResponseBuilder.build(HttpStatus.CREATED, "Ingreso casual creado");
    }

    private MailResponse sendEmail(Income income) throws FirebaseMessagingException {
        MailResponse response = null;
        if(income.getResidence().getUser() != null){
            response = mailService.sendMailIncomeCreation(income.getResidence().getUser(), income);
            HashMap<String, String> pushData = new HashMap<>();
            pushData.put("id", income.getId().toString());
            pushNotificationService.sendPushNotification(income.getResidence().getUser().getId(), income.getType().getName(), "Se ha enviado a su correo electrónico el comprobante de cobro", pushData);
        }
        return response;
    }

    public ResponseEntity<CustomResponse<?>> updateIncomeCasual(Long id, IncomeCasualUpdateData data) throws IOException, FileUploadException, FirebaseMessagingException {
        Income income = incomeService.updateIncomeCasual(id, data);
        MailResponse response = sendEmail(income);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso casual actualizado");
    }
}
