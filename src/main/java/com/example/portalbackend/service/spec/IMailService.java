package com.example.portalbackend.service.spec;

import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.User;

public interface IMailService {

    MailResponse sendMailRecoveryPassword(User user, String newPassword);
    MailResponse sendMailIncomeCreation(User user, Income income);
    MailResponse sendMailPenalty(User user, Penalty penalty);
}
