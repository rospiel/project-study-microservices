package com.study.microservices.studyapplication.infrastructure.mail;

import com.study.microservices.studyapplication.infrastructure.mail.model.Message;

public interface MailService {

    void send(Message message);

}
