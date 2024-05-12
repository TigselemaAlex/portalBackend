package com.example.portalbackend.api.usecase;

import com.amazonaws.Response;
import com.example.portalbackend.api.dto.request.convocation.ConvocationAttendanceData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationCreateData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationParticipantAttendanceData;
import com.example.portalbackend.api.dto.request.convocation.ConvocationUpdateData;
import com.example.portalbackend.api.dto.request.geolocation.GeolocationData;
import com.example.portalbackend.api.dto.response.PageResponse;
import com.example.portalbackend.api.dto.response.convocation.ConvocationParticipantsResponse;
import com.example.portalbackend.api.dto.response.convocation.ConvocationResponse;
import com.example.portalbackend.api.dto.response.notification.NotificationResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.Convocation;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.service.spec.IConvocationService;
import com.example.portalbackend.service.spec.IUserService;
import com.example.portalbackend.util.enumerate.ConvocationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import static com.example.portalbackend.util.geolocation.GeolocationUtils.calculateHaversineDistance;

@Component
public class ConvocationUseCase extends AbstractUseCase {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final IConvocationService convocationService;
    private static final String CONFIG_FILE_PATH = "geolocation-config.json";
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;
    private final IUserService userService;

    protected ConvocationUseCase(CustomResponseBuilder customResponseBuilder, SimpMessagingTemplate simpMessagingTemplate, IConvocationService convocationService, ResourceLoader resourceLoader, ObjectMapper objectMapper, IUserService userService) {
        super(customResponseBuilder);
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.convocationService = convocationService;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    public ResponseEntity<CustomResponse<?>> createConvocation(ConvocationCreateData data) {
        Convocation convocation = convocationService.createConvocation(data);
        simpMessagingTemplate.convertAndSend("/topic/notification", new NotificationResponse("Convocatoria", "Se ha creado una nueva convocatoria", convocation.getCreatedBy().getNames()+ " " + convocation.getCreatedBy().getSurnames()));
        return customResponseBuilder.build(HttpStatus.CREATED, "Convocatoria creada exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> findAll(String subject, Long start, Long end, ConvocationType type, Pageable pageable) {
        Page<ConvocationResponse> response = convocationService
                .findAll(subject, start, end, type, pageable)
                .map(convocation ->
                        {
                            return new ConvocationResponse(convocation, 303 - convocationService.countTotalMissing(convocation.getId()), convocationService.countTotalMissing(convocation.getId()));
                        }
                );
        PageResponse pageResponse = new PageResponse(response);
        return customResponseBuilder.build(HttpStatus.OK, "Convocatorias encontradas exitosamente", pageResponse);
    }

    public ResponseEntity<CustomResponse<?>> updateConvocation (Long id, ConvocationUpdateData data) {
        Convocation convocation = convocationService.updateConvocation(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Convocatoria actualizada exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> deleteConvocation(Long id) {
        convocationService.deleteConvocation(id);
        return customResponseBuilder.build(HttpStatus.OK, "Convocatoria eliminada exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> findAllParticipants(Long id) {
        List<ConvocationParticipantsResponse> responses = convocationService.findAllParticipants(id).stream().map(ConvocationParticipantsResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Participantes encontrados exitosamente", responses);
    }

    public ResponseEntity<CustomResponse<?>> finalizeConvocation(Long id) {
        convocationService.finalizeConvocation(id);
        return customResponseBuilder.build(HttpStatus.OK, "Convocatoria finalizada exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> updateAttendance(Long id, ConvocationAttendanceData data) {
        ConvocationParticipant participant = convocationService.updateAttendance(id, data);
        if (participant == null) {
            return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "Error al actualizar la asistencia, verifique los datos ingresados");
        }
        return customResponseBuilder.build(HttpStatus.OK, "Asistencia actualizada exitosamente");
    }

    public ResponseEntity<CustomResponse<?>> findFirstByDateBetweenAndFinalizedIsFalse() {
        Convocation convocation = convocationService.findFirstByDateBetweenAndFinalizedIsFalse();
        if (Objects.isNull(convocation)) {
            return customResponseBuilder.build(HttpStatus.NOT_FOUND, "No se encontraron convocatorias para hoy");
        }
        return customResponseBuilder.build(HttpStatus.OK, "Convocatoria encontrada exitosamente", new ConvocationResponse(convocation, 303 - convocationService.countTotalMissing(convocation.getId()), convocationService.countTotalMissing(convocation.getId())));
    }


    public ResponseEntity<CustomResponse<?>> findAllByConvocationIdAndResidenceUserId(Long id, Long userId) {
        List<ConvocationParticipantsResponse> responses = convocationService.findAllByConvocationIdAndResidenceUserId(id, userId).stream().map(ConvocationParticipantsResponse::new).toList();
        return customResponseBuilder.build(HttpStatus.OK, "Participantes encontrados exitosamente", responses);
    }


    public ResponseEntity<CustomResponse<?>> setParticipantAttendance(Long id, ConvocationParticipantAttendanceData attendanceData) throws IOException {
        GeolocationData data = null;
        Path path = Path.of("src/main/resources/" + CONFIG_FILE_PATH);
        if (path.toFile().exists()) {
            Resource resource = resourceLoader.getResource("file:" + path);
            data = objectMapper.readValue(resource.getInputStream(), GeolocationData.class);
        }
        if (data == null) {
            return customResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la configuración de geolocalización");
        }else{
            BigDecimal pointLatitud = data.latitude();
            BigDecimal pointLongitud = data.longitude();
            Integer radius = data.radius();
            double distance = calculateHaversineDistance(pointLatitud.doubleValue(), pointLongitud.doubleValue(), attendanceData.latitude().doubleValue(), attendanceData.longitude().doubleValue());
            boolean isInside = distance <= radius;
            if(isInside) {
                ConvocationAttendanceData convocationAttendanceData = new ConvocationAttendanceData(attendanceData.residence(), true, null);
                ConvocationParticipant participant = convocationService.updateAttendanceParticipant(id, convocationAttendanceData);
                if (participant == null) {
                    return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "Error al actualizar la asistencia, verifique que la asamblea aun no haya finalizado o que este en el tiempo límite para actualizar la asistencia");
                }
                return customResponseBuilder.build(HttpStatus.OK, "Asistencia actualizada exitosamente");
            }else{
                return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "No se encuentra en el rango de la geolocalización");
            }
        }

    }


}
