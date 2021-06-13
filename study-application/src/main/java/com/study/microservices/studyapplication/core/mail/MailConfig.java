package com.study.microservices.studyapplication.core.mail;

import com.study.microservices.studyapplication.domain.service.notification.NotificationPostedCityListener;
import com.study.microservices.studyapplication.infrastructure.mail.GmailServiceImpl;
import com.study.microservices.studyapplication.infrastructure.mail.MailService;
import com.study.microservices.studyapplication.infrastructure.mail.SendGridMailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Properties;

import static com.study.microservices.studyapplication.core.mail.MailProperties.MailServiceType.GMAIL;
import static com.study.microservices.studyapplication.core.mail.MailProperties.MailServiceType.SENDGRID;


@Configuration
@EnableAsync
@EnableJpaAuditing
@RequiredArgsConstructor
public class MailConfig {

    private final MailProperties mailProperties;

    @Bean
    public MailService mailService() {
        return  SENDGRID.equals(mailProperties.getMailServiceType()) ?
                new SendGridMailServiceImpl() :
                new GmailServiceImpl();
    }

    /**
     * Necessary because our structure was to have more than a mail service, normally
     * we pass host and other properties in a yml file
     * @return
     */
    @Bean
    public JavaMailSender primarySender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        if (SENDGRID.equals(mailProperties.getMailServiceType())) {
            javaMailSender.setHost(mailProperties.getSendGrid().getHost());
            javaMailSender.setPort(Integer.valueOf(mailProperties.getSendGrid().getPort()));
            javaMailSender.setUsername(mailProperties.getSendGrid().getUsername());
            javaMailSender.setPassword(mailProperties.getSendGrid().getPassword());
            return javaMailSender;
        }

        addPropertiesGmail(javaMailSender);
        javaMailSender.setHost(mailProperties.getGmail().getHost());
        javaMailSender.setPort(Integer.valueOf(mailProperties.getGmail().getPort()));
        javaMailSender.setUsername("rospielberg@gmail.com");
        javaMailSender.setPassword("");
        return javaMailSender;
    }

    private void addPropertiesGmail(JavaMailSenderImpl javaMailSender) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", mailProperties.getGmail().isPropertiesMailSmtpAuth());
        properties.put("mail.smtp.starttls.enable", mailProperties.getGmail().isPropertiesMailSmtpStarttlsEnable());
        properties.put("mail.properties.mail.smtp.ssl.trust", mailProperties.getGmail().getPropertiesMailSmtpSslTrust());
        javaMailSender.setJavaMailProperties(properties);
    }

    @Bean
    private AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }
}


