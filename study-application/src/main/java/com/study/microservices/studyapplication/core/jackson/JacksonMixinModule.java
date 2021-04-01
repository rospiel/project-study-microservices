package com.study.microservices.studyapplication.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.study.microservices.studyapplication.api.controller.model.mixin.CityMixin;
import com.study.microservices.studyapplication.api.controller.model.mixin.RestaurantMixin;
import com.study.microservices.studyapplication.domain.dto.CityDto;
import com.study.microservices.studyapplication.domain.dto.RestaurantDto;
import org.springframework.stereotype.Component;

@Component
/* SimpleModulo to access a method to put together all annotation */
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        /* here we teach it how to put together annotation from dto and mixin */
        setMixInAnnotation(RestaurantDto.class, RestaurantMixin.class);
        setMixInAnnotation(CityDto.class, CityMixin.class);
    }
}
