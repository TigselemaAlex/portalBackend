package com.example.portalbackend.api.usecase;

import com.example.portalbackend.api.dto.request.geolocation.GeolocationData;
import com.example.portalbackend.api.dto.response.notification.NotificationResponse;
import com.example.portalbackend.common.CustomResponse;
import com.example.portalbackend.common.CustomResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class GeolocationUseCase extends AbstractUseCase{

    private final SimpMessagingTemplate simpMessagingTemplate;
    private static final String CONFIG_FILE_PATH = "geolocation-config.json";
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    protected GeolocationUseCase(CustomResponseBuilder customResponseBuilder, SimpMessagingTemplate simpMessagingTemplate, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        super(customResponseBuilder);
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<GeolocationData> getConfig() throws IOException {
        GeolocationData data = null;
        Path path = Path.of("src/main/resources/" + CONFIG_FILE_PATH);
        if (path.toFile().exists()) {
            Resource resource = resourceLoader.getResource("file:" + path);
            data = objectMapper.readValue(resource.getInputStream(), GeolocationData.class);
        }
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(data);
    }

    public ResponseEntity<CustomResponse<?>> updateConfig(GeolocationData data) throws IOException {
        objectMapper.writeValue(new File("src/main/resources/" + CONFIG_FILE_PATH), data);
        simpMessagingTemplate.convertAndSend("/topic/notification", new NotificationResponse("Geolocalización", "Se ha modificado el punto de reuniones", null));
        return customResponseBuilder.build(HttpStatus.OK, "Configuración actualizada correctamente");
    }


}
