package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.income.IncomeFeesCreateData;
import com.example.portalbackend.api.dto.request.income.IncomeFeesUpdateData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.income.IncomeResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.IncomeType;
import com.example.portalbackend.domain.entity.Residence;
import com.example.portalbackend.domain.exception.FileUploadException;
import com.example.portalbackend.service.spec.IIncomeService;
import com.example.portalbackend.service.spec.IIncomeTypeService;
import com.example.portalbackend.service.spec.IResidenceService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IncomeUseCase extends AbstractUseCase{

    private final IIncomeService incomeService;
    private final IIncomeTypeService incomeTypeService;
    private final IResidenceService residenceService;

    protected IncomeUseCase(CustomResponseBuilder customResponseBuilder, IIncomeService incomeService, IIncomeTypeService incomeTypeService, IResidenceService residenceService) {
        super(customResponseBuilder);
        this.incomeService = incomeService;

        this.incomeTypeService = incomeTypeService;
        this.residenceService = residenceService;
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

    public ResponseEntity<CustomResponse<?>> saveIncomeFees(IncomeFeesCreateData data) throws IOException, FileUploadException {
        incomeService.saveIncomeFees(data);
        return customResponseBuilder.build(HttpStatus.CREATED, "Ingreso mensual creado");
    }

    public ResponseEntity<CustomResponse<?>> updateIncomeFees(Long id, IncomeFeesUpdateData data) throws IOException, FileUploadException {
        incomeService.updateIncomeFees(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso mensual actualizado");
    }

    public ResponseEntity<CustomResponse<?>> deleteIncome(Long id) {
        incomeService.deleteIncome(id);
        return customResponseBuilder.build(HttpStatus.OK, "Ingreso eliminado");
    }
}
