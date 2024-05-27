package com.example.portalbackend.api.dto.request.mail;

import com.example.portalbackend.domain.entity.User;

public record SendMailData(
        User user

) {
}
