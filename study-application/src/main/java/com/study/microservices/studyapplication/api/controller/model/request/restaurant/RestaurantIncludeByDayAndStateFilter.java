package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Getter
@Setter
public class RestaurantIncludeByDayAndStateFilter {

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime creationDate;

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime endCreationDate;

    private Boolean enable;

    private String stateName;
}
