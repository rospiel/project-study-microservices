package com.study.microservices.studyapplication.core.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.study.microservices.studyapplication.core.mail.MailProperties.MailServiceType.GMAIL;
import static com.study.microservices.studyapplication.core.mail.MailProperties.MailServiceType.SENDGRID;

/**
 * A mirror of the properties in yml file
 */
@Getter
@Setter
@Component
@ConfigurationProperties("spring.mail")
public class MailProperties {

    private MailProperties.SendGrid sendGrid = new MailProperties.SendGrid();
    private MailProperties.Gmail gmail = new MailProperties.Gmail();

    /* get from yml file a mail service to use */
    @Value("${studyapplication.mail.service}")
    private MailProperties.MailServiceType mailServiceType = GMAIL;

    /**
     * Every new mail service must have their enum representation
     */
    public enum MailServiceType {
        SENDGRID, GMAIL
    }

    @Getter
    @Setter
    public class SendGrid {
        private String host;
        private String port;
        private String username;
        private String password;
        private String sender;
    }

    @Getter
    @Setter
    public class Gmail {
        private String host;
        private String port;
        private String username;
        private String password;
        private String protocol;
        private boolean propertiesMailSmtpAuth;
        private boolean propertiesMailSmtpStarttlsEnable;
        private String propertiesMailSmtpSslTrust;

    }

}
