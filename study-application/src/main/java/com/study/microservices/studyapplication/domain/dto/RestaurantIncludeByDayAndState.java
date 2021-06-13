package com.study.microservices.studyapplication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class RestaurantIncludeByDayAndState {

    private final String stateName;
    private final Date dateCreate;
    private final Long amount;
}
