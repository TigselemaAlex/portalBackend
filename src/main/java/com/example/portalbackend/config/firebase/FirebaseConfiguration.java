package com.example.portalbackend.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
@RequiredArgsConstructor
public class FirebaseConfiguration {

    private final FirebaseProperties firebaseProperties;

    @Bean
    public GoogleCredentials googleCredentials(){
        try {
            if (firebaseProperties.getServiceAccount() != null) {
                try(InputStream is = firebaseProperties.getServiceAccount().getInputStream()){
                    return GoogleCredentials.fromStream(is);
                }
            }else{
                return GoogleCredentials.getApplicationDefault();
            }
        }catch (IOException ex){
            throw new RuntimeException("Error al cargar las credenciales de Firebase", ex);
        }
    }

    @Bean
    public FirebaseApp firebaseApp(GoogleCredentials googleCredentials){

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options);
            }
            return FirebaseApp.getInstance();

    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp){
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
