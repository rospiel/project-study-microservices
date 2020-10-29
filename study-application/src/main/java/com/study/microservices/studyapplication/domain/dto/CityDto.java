package com.study.microservices.studyapplication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CityDto {

    private Long id;
    private String name;
    private StateDto state;
}
