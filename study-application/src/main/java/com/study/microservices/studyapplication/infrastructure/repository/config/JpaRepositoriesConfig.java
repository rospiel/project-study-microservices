package com.study.microservices.studyapplication.infrastructure.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.study.microservices.studyapplication.infrastructure.repository",
                                       "com.study.microservices.studyapplication.domain.repository"})
public class JpaRepositoriesConfig {
}
