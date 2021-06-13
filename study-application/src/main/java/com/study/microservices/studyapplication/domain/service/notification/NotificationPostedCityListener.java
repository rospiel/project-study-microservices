package com.study.microservices.studyapplication.domain.service.notification;

import com.study.microservices.studyapplication.domain.service.event.PostedCityEvent;
import com.study.microservices.studyapplication.infrastructure.mail.MailService;
import com.study.microservices.studyapplication.infrastructure.mail.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificationPostedCityListener {

    private final MailService mailService;

    @Async
    @EventListener
    public void afterPostedCity(PostedCityEvent event) {
        Message message = Message.builder()
                .body("newCity.html")
                .subject("New City Posted")
                .recipients(Set.of("rodrigospielberg@hotmail.com"))
                .property("city", event.getCity())
                .build();

        mailService.send(message);
    }
}
