package com.study.microservices.studyapplication.domain.service.event;

import com.study.microservices.studyapplication.domain.dto.CityDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostedCityEvent {

    private final CityDto city;
}
