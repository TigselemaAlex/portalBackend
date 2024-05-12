package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.convocation.ConvocationAttendanceData;
import com.example.portalbackend.api.dto.request.geolocation.GeolocationData;
import com.example.portalbackend.api.dto.request.vote.AssemblyQuestionData;
import com.example.portalbackend.api.dto.request.vote.ParticipantVoteData;
import com.example.portalbackend.api.dto.response.vote.AssemblyQuestionResponse;
import com.example.portalbackend.api.dto.response.vote.ParticipantVoteResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.example.portalbackend.domain.entity.AssemblyQuestion;
import com.example.portalbackend.domain.entity.ConvocationParticipant;
import com.example.portalbackend.domain.entity.ParticipantVote;
import com.example.portalbackend.service.spec.IAssemblyQuestionService;
import com.example.portalbackend.service.spec.IPushNotificationService;
import com.example.portalbackend.util.enumerate.PushNotificationAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import static com.example.portalbackend.util.geolocation.GeolocationUtils.calculateHaversineDistance;

@Component
public class AssemblyQuestionUseCase extends AbstractUseCase{

    private final IAssemblyQuestionService assemblyQuestionService;
    private final IPushNotificationService pushNotificationService;
    private static final String CONFIG_FILE_PATH = "geolocation-config.json";
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    protected AssemblyQuestionUseCase(CustomResponseBuilder customResponseBuilder, IAssemblyQuestionService assemblyQuestionService, IPushNotificationService pushNotificationService, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        super(customResponseBuilder);
        this.assemblyQuestionService = assemblyQuestionService;
        this.pushNotificationService = pushNotificationService;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<CustomResponse<?>> createAssemblyQuestion(AssemblyQuestionData data) {
        assemblyQuestionService.createAssemblyQuestion(data);
        return customResponseBuilder.build(HttpStatus.CREATED, "Pregunta de asamblea creada con éxito");
    }

    public ResponseEntity<CustomResponse<?>> findAllAssemblyQuestions(Long id) {
        List<AssemblyQuestionResponse> responses = assemblyQuestionService.findAllAssemblyQuestions(id).stream()
                .map(AssemblyQuestionResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK, "Preguntas de asamblea encontradas con éxito", responses);
    }

    public ResponseEntity<CustomResponse<?>> findAllByConvocationAndEnabledIsTrueOrderById(Long id) {
        List<AssemblyQuestionResponse> responses = assemblyQuestionService.findAllByConvocationAndEnabledIsTrueOrderById(id).stream()
                .map(AssemblyQuestionResponse::new)
                .toList();
        return customResponseBuilder.build(HttpStatus.OK, "Preguntas de asamblea encontradas con éxito", responses);
    }

    public ResponseEntity<CustomResponse<?>> updateAssemblyQuestion(Long id, AssemblyQuestionData data) {
        assemblyQuestionService.updateAssemblyQuestion(id, data);
        return customResponseBuilder.build(HttpStatus.OK, "Pregunta de asamblea actualizada con éxito");
    }

    public ResponseEntity<CustomResponse<?>> deleteAssemblyQuestion(Long id) {
        assemblyQuestionService.deleteAssemblyQuestion(id);
        return customResponseBuilder.build(HttpStatus.OK, "Pregunta de asamblea eliminada con éxito");
    }

    public  ResponseEntity<CustomResponse<?>> toggleEnabledVote(Long id) throws FirebaseMessagingException {
        AssemblyQuestion assemblyQuestion = assemblyQuestionService.toggleEnabledVote(id);
        String title = assemblyQuestion.getEnabled() ? "Voto habilitado" : "Voto deshabilitado";
        String message = "Pregunta: " + assemblyQuestion.getQuestion();
        HashMap<String, String> data = new HashMap<>();
        data.put("action", PushNotificationAction.ASSEMBLY_QUESTION_ENABLED_VOTE_TOGGLED.name());
        pushNotificationService.sendPushNotificationAllClients(title, message, data);
        AssemblyQuestionResponse response = new AssemblyQuestionResponse(assemblyQuestion);
        return customResponseBuilder.build(HttpStatus.OK, title + " con éxito", response);
    }

    public ResponseEntity<CustomResponse<?>> updateVote(ParticipantVoteData voteData) throws IOException {

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
            double distance = calculateHaversineDistance(pointLatitud.doubleValue(), pointLongitud.doubleValue(), voteData.geolocation().latitude(), voteData.geolocation().longitude());
            boolean isInside = distance <= radius;
            if(isInside) {
                AssemblyQuestion assemblyQuestion = assemblyQuestionService.updateVote(voteData);
                if (assemblyQuestion == null) {
                    return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "No puede votar en esta pregunta de la asamblea");
                }
                AssemblyQuestionResponse response = new AssemblyQuestionResponse(assemblyQuestion);
                return customResponseBuilder.build(HttpStatus.OK, "Voto actualizado con éxito", response);
            }else{
                return customResponseBuilder.build(HttpStatus.BAD_REQUEST, "No se encuentra en el rango de la geolocalización");
            }
        }

    }

    public ResponseEntity<CustomResponse<?>> getParticipantVote(Long id, Long userId) {
        ParticipantVote participantVote = assemblyQuestionService.getParticipantVote(id, userId);
        if (participantVote == null) {
            return customResponseBuilder.build(HttpStatus.OK, "Aun no ha votado");
        }
        ParticipantVoteResponse response = new ParticipantVoteResponse(participantVote);
        return customResponseBuilder.build(HttpStatus.OK, "Voto encontrado con éxito", response);
    }
}
