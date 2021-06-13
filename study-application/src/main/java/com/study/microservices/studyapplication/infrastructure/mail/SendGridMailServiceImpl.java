package com.study.microservices.studyapplication.infrastructure.mail;

import com.study.microservices.studyapplication.core.mail.MailProperties;
import com.study.microservices.studyapplication.infrastructure.mail.exception.MailException;
import com.study.microservices.studyapplication.infrastructure.mail.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static java.lang.String.format;

public class SendGridMailServiceImpl implements MailService {

    /* Class from spring mail to help */
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(mailProperties.getSendGrid().getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
        } catch (Exception error) {
            String errorMessage = "Error during send a mail message.: %s.";
            throw new MailException(format(errorMessage, message.getBody()), error);
        }
    }
}
