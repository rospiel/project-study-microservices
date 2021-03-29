package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class CityDto {

    private Long id;

    @NotBlank
    private String name;

    @Valid
    @NotNull
    @ConvertGroup(from = Default.class, to = Groups.IncludeCity.class)
    private StateDto state;
}
