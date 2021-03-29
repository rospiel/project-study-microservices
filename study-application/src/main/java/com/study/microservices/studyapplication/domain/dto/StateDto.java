package com.study.microservices.studyapplication.domain.dto;

import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class StateDto {

    @NotNull(groups = {Groups.IncludeCity.class})
    private Long id;

    @NotBlank
    private String name;
}
