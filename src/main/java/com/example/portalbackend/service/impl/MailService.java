package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.domain.entity.Income;
import com.example.portalbackend.domain.entity.Penalty;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.service.spec.IMailService;
import com.example.portalbackend.util.calendar.CalendarUtil;
import com.example.portalbackend.util.number.NumberUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender sender;
    private final Configuration config;


    @Override
    public MailResponse sendMailRecoveryPassword(User user, String newPassword) {
        Map<String, Object> model = createModel(user, newPassword);
        MailResponse mailResponse = null;
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template template = config.getTemplate("recover-password.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject("Recuperación de contraseña");
            sender.send(message);
            mailResponse = new MailResponse("Mensaje enviado exitosamente, revise su bandeja de entrada para más información", Boolean.TRUE);
        }catch (MessagingException | IOException | TemplateException e){
            mailResponse = new MailResponse("Error al enviar el mensaje : "+ e.getMessage(), Boolean.FALSE);
        }
        return mailResponse;
    }

    @Override
    public MailResponse sendMailIncomeCreation(User user, Income income) {
        Map<String, Object> model = createModel(user, income);
        MailResponse mailResponse = null;
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template template = config.getTemplate("recipe.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject("Comprobante de cobro");
            sender.send(message);
            mailResponse = new MailResponse("Mensaje enviado exitosamente, revise su bandeja de entrada para más información", Boolean.TRUE);
        }catch (MessagingException | IOException | TemplateException e){
            mailResponse = new MailResponse("Error al enviar el mensaje : "+ e.getMessage(), Boolean.FALSE);
        }
        return mailResponse;
    }

    @Override
    public MailResponse sendMailPenalty(User user, Penalty penalty) {
        Map<String, Object> model = createModel(user, penalty);
        MailResponse mailResponse = null;
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Template template = config.getTemplate("recipe.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setTo(user.getEmail());
            helper.setText(html, true);
            helper.setSubject("Comprobante de cobro");
            sender.send(message);
            mailResponse = new MailResponse("Mensaje enviado exitosamente, revise su bandeja de entrada para más información", Boolean.TRUE);
        }catch (MessagingException | IOException | TemplateException e){
            mailResponse = new MailResponse("Error al enviar el mensaje : "+ e.getMessage(), Boolean.FALSE);
        }
        return mailResponse;
    }

    private Map<String, Object> createModel(User user, Penalty penalty){
        Map<String, Object> model = new HashMap<>();
        model.put("code", penalty.getCode());
        model.put("fullname", user.getSurnames() + " " + user.getNames());
        model.put("amount", penalty.getAmount());
        model.put("number", penalty.getResidence().getNumber());
        model.put("quantity_letters", NumberUtils.convert(penalty.getAmount().intValue()));
        model.put("concept",
                penalty.getType().getName() + "(Multa).");
        model.put("day", String.format("%02d",
                penalty.getPaidDate().get(Calendar.DAY_OF_MONTH)));
        model.put("month",
                String.format("%02d",
                        penalty.getPaidDate().get(Calendar.MONTH) + 1));
        model.put("year", String.valueOf(penalty.getPaidDate().get(Calendar.YEAR)).replaceAll(",", ""));
        return model;
    }


    private Map<String, Object> createModel(User user, Income income){
        Map<String, Object> model = new HashMap<>();
        model.put("code", income.getCode());
        model.put("fullname", user.getSurnames() + " " + user.getNames());
        model.put("amount", income.getAmount());
        model.put("number", income.getResidence().getNumber());
        model.put("quantity_letters", NumberUtils.convert(income.getAmount().intValue()));
        String monthRange = "";
        if(Objects.nonNull(income.getPaidSince()) && Objects.nonNull(income.getPaidUntil())) {

            if (income.getMonthsPaid() > 1) {
                monthRange = CalendarUtil.getMonthAndYearSpanishTranslation(income.getPaidSince()) + " hasta " + CalendarUtil.getMonthAndYearSpanishTranslation(income.getPaidUntil());
            } else {
                monthRange = "del mes " + CalendarUtil.getMonthAndYearSpanishTranslation(income.getPaidSince());
            }
        }
            model.put("concept",
                    income.getType().getName()+ ", " +  monthRange);

        model.put("day", String.format("%02d",
                income.getPaidDate().get(Calendar.DAY_OF_MONTH)));
        model.put("month",
                String.format("%02d",
                income.getPaidDate().get(Calendar.MONTH) + 1));
        model.put("year", String.valueOf(income.getPaidDate().get(Calendar.YEAR)).replaceAll(",", ""));
        return model;
    }


    private Map<String, Object> createModel(User user, String password){
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getSurnames() + " " + user.getNames());
        model.put("password", password);
        return model;
    }
}
