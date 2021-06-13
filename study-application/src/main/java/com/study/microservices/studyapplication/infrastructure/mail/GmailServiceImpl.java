package com.study.microservices.studyapplication.infrastructure.mail;

import com.study.microservices.studyapplication.core.mail.MailProperties;
import com.study.microservices.studyapplication.domain.service.event.PostedCityEvent;
import com.study.microservices.studyapplication.infrastructure.mail.exception.MailException;
import com.study.microservices.studyapplication.infrastructure.mail.model.Message;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

import java.util.Set;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;

public class GmailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(mailProperties.getSendGrid().getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());

            String bodyProcessed = processTemplate(message);
            helper.setText(bodyProcessed, TRUE);
            mailSender.send(mimeMessage);
        } catch (Exception error) {
            String errorMessage = "Error during send a mail message.: %s.";
            throw new MailException(format(errorMessage, message.getBody()), error);
        }
    }

    private String processTemplate(Message message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getProperties());
        } catch (Exception error) {
            String errorMessage = "Error during process the template.: %s.";
            throw new MailException(format(errorMessage, message.getBody()), error);
        }
    }
}
