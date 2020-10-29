package com.study.microservices.studyapplication.domain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class KitchenDto {

    private Long id;
    private String name;
}
