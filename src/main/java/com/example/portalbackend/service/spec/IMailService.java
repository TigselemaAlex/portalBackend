package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.domain.entity.User;

import java.util.Map;

public interface IMailService {

    MailResponse sendMail(User user, String newPassword);
}
