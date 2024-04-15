package com.example.portalbackend.service.impl;

import com.example.portalbackend.api.dto.response.mail.MailResponse;
import com.example.portalbackend.domain.entity.User;
import com.example.portalbackend.service.spec.IMailService;
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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender sender;
    private final Configuration config;


    @Override
    public MailResponse sendMail(User user, String newPassword) {
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

    private Map<String, Object> createModel(User user, String password){
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getSurnames() + " " + user.getNames());
        model.put("password", password);
        return model;
    }
}
