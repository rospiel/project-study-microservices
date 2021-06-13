package com.study.microservices.studyapplication.core.jmapper;

import com.googlecode.jmapper.api.JMapperAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JMapperConfig {

    @Bean
    public JMapperAPI JMapper() {
        return new JMapperAPI();
    }
}
