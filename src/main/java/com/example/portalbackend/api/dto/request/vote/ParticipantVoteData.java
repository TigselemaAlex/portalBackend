package com.example.portalbackend.api.dto.request.vote;

import jakarta.validation.constraints.NotNull;

public record ParticipantVoteData(
        @NotNull(message = "El voto no puede ser nulo")
        Boolean vote,
        @NotNull(message = "La pregunta de la asamblea no puede ser nula")
        Long assemblyQuestion,
        @NotNull(message = "El participante no puede ser nulo")
        Long voteBy,
        @NotNull(message = "La geolocalización no puede ser nula")
        GeolocationDataP geolocation,
        @NotNull (message = "El dispositivo no puede ser nulo")
        String deviceId
) {

        public record GeolocationDataP(
                Double latitude,
                Double longitude
        ) {
        }
}


