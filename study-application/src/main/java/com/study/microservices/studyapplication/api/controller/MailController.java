package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.infrastructure.mail.MailService;
import com.study.microservices.studyapplication.infrastructure.mail.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;

import java.util.Set;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/mails", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/welcome/{email}")
    @ResponseStatus(OK)
    public void send(@PathVariable @Email String email) {
        Message message = Message.builder()
                .body("welcome.html")
                .subject("Welcome!")
                .recipients(Set.of(email))
                .property("mailRecipient", email)
                .build();
        mailService.send(message);
    }
}
